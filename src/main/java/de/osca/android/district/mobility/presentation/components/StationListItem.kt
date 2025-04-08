package de.osca.android.district.mobility.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.mobility.data.model.BusStation
import de.osca.android.district.mobility.navigation.MobilityNavItems
import de.osca.android.essentials.utils.extensions.getLastDeviceLocation

@Composable
fun StationListItem(busStation: BusStation) {
    LaunchedEffect(busStation) {}

    var distance: Float = -1.0F
    val context = LocalContext.current

    context.getLastDeviceLocation { result ->
        result?.let { latLng ->
            val erge: FloatArray = FloatArray(3)

            busStation.geopoint?.x.let {
                android.location.Location.distanceBetween(
                    busStation.geopoint!!.x,
                    busStation.geopoint.y,
                    latLng.latitude,
                    latLng.longitude,
                    erge,
                )
                distance = erge[0]
            }
        }
    }

    NavigationLink(
        navigate = MobilityNavItems.MobilityStationDetailNavItem(busStation.id),
        Modifier
            .fillMaxWidth(),
        enabled = true,
    ) {
        Column(Modifier.fillMaxWidth()) {
            MobilityListItem(
                iconUrl = busStation.iconUrl,
                name = busStation.name ?: "",
                details = "${busStation.address} ${busStation.androidDeeplink} ${busStation.providerName} ${busStation.stops} ${busStation.locationType}",
                entfernung = if (distance < 0) "Berechnung nicht möglich." else distance.toString(),
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(DistrictDesign.Padding.BIG),
            ) {
                busStation.stops.forEachIndexed { index, stop ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(0.1F)
                                .fillMaxHeight()
                                .padding(DistrictDesign.Padding.MEDIUM)
                                .border(2.dp, Color.primary, DistrictDesign.ROUNDED_SHAPE),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                        ) {
                            /*
                            stop.iconUrl?.let {
                                RemoteSVGImage(
                                    it,
                                    modifier =
                                        Modifier
                                            .size(DistrictDesign.Size.Icon.BIG),
                                )
                            }*/
                        }
                        Column(
                            Modifier
                                .fillMaxWidth(0.8F),
                        ) {
                            Text(
                                stop.name,
                                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                stop.information,
                                fontSize = DistrictDesign.Size.Font.SMALL_TEXT,
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxWidth(),
                        ) {
                            Text(
                                "Min",
                                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                                fontWeight = FontWeight.Bold,
                            )
                            Text("Uhrzeit", fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
                        }
                    }
                    if (index < (busStation.stops.size - 1)) HorizontalDivider(thickness = 2.dp)
                }
            }
        }
    }
}
