package de.osca.android.district.mobility.presentation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.mobility.data.model.Vehicle
import de.osca.android.district.mobility.navigation.MobilityNavItems
import de.osca.android.essentials.utils.extensions.getLastDeviceLocation

@Composable
fun VehicleListItem(
    vehicle: Vehicle,
) {
    LaunchedEffect(vehicle) {}

    var distance: Float = -1.0F
    val context = LocalContext.current

    context.getLastDeviceLocation { result ->
        result?.let { latLng ->
            val erge: FloatArray = FloatArray(3)

            vehicle.geopoint?.x.let {
                android.location.Location.distanceBetween(
                    vehicle.geopoint!!.x,
                    vehicle.geopoint.y,
                    latLng.latitude,
                    latLng.longitude,
                    erge
                )
                distance = erge[0]
            }
        }
    }


    NavigationLink(
        navigate = MobilityNavItems.MobilityVehicleDetailNavItem(vehicle.id),
        Modifier.fillMaxWidth(),
        enabled = true,
    ) {
        MobilityListItem(
            iconUrl = vehicle.imageUrl,
            name = vehicle.name ?: "",
            details = "${vehicle.information} ${vehicle.batteryPercentage} ${vehicle.address} ${vehicle.androidDeeplink} ${vehicle.webUrl} ${vehicle.color} ${vehicle.description} ${vehicle.remainingRangeInMeter} ${vehicle.plateNumber}",

            entfernung = if (distance <0) "Berechnung nicht möglich." else distance.toString()
        )
    }
}
