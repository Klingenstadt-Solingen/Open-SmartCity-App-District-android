package de.osca.android.district.event.presentation.components

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.parse.ParseGeoPoint
import de.osca.android.district.R
import de.osca.android.district.core.data.toLatLng
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.core.util.startMapIntent
import de.osca.android.essentials.utils.extensions.getLastDeviceLocation


@Composable
fun EventLocationNavigation(
    locationDescription: String,
    geopoint: ParseGeoPoint,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val distance = remember { mutableStateOf("") }

    context.getLastDeviceLocation { result ->
        result?.let{ latLng ->
            val results = FloatArray(3)
            Location.distanceBetween(
                geopoint.latitude,
                geopoint.longitude,
                latLng.latitude,
                latLng.longitude,
                results
            )

            if (results.isNotEmpty()) {
                distance.value = if (results[0] < 1000) {
                    context.getString(R.string.meter_distance, results[0])
                } else {
                    context.getString(R.string.kilometer_distance, results[0].div(1000))
                }
            }
        }
    }

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)
    ) {
        Text(
            stringResource(id = R.string.location_where),
            fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    locationDescription,
                    fontSize = DistrictDesign.Size.Font.DEFAULT,
                    maxLines = 2
                )
                Text(
                    distance.value,
                    fontSize = DistrictDesign.Size.Font.NORMAL_TEXT
                )
            }
            GeneralButton(
                modifier = Modifier,
                onClick = {
                    startMapIntent(position = geopoint.toLatLng(), context = context)
                },
                contentPadding = PaddingValues(DistrictDesign.Padding.MEDIUM),
                colors = CardDefaults.cardColors(containerColor = Color.accentColor),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navigation),
                        contentDescription = null,
                        modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                        tint = Color.primary,
                    )
                },
            )
        }
    }
}