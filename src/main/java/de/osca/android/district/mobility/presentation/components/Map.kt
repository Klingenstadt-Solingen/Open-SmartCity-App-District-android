package de.osca.android.district.mobility.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.district.mobility.data.model.Mobility
import de.osca.android.district.pointofinterest.presentation.component.map.PoiTileOverlay
import de.osca.android.essentials.domain.entity.Coordinates

@ExperimentalMaterial3Api
@Composable
fun Map(
    onClick: () -> Unit = {},
    onMarkerClick: (objectId: String) -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth().height(300.dp),
    initLatLng: LatLng = LatLng(51.170975, 7.083238),
    initZoom: Float = 13f,
    mapSettings: MapUiSettings =
        MapUiSettings(
            compassEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = false,
            tiltGesturesEnabled = false,
            mapToolbarEnabled = false,
            indoorLevelPickerEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
        ),
    userPosition: Coordinates? = null,
    mobilities: List<Mobility>? = null,
) {
    val cameraPosition =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(initLatLng, initZoom)
        }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        properties =
            MapProperties(
                mapStyleOptions = null,
                mapType = MapType.NORMAL,
            ),
        uiSettings = mapSettings,
        onMapClick = {
            onClick()
        },
        onMapLoaded = {
            // ...
        },
    ) {
        TileOverlay(tileProvider = PoiTileOverlay(), zIndex = -100F)

        mobilities?.forEach { mobilityObject ->
            mobilityObject.geopoint?.x?.let {
                MarkerComposable(
                    title = mobilityObject.name,
                    state =
                        MarkerState(
                            LatLng(
                                mobilityObject.geopoint!!.x,
                                mobilityObject.geopoint!!.y,
                            ),
                        ),
                    onClick = {
                        onMarkerClick(mobilityObject.id)
                        true
                    },
                    contentDescription = "",
                    zIndex = 10F,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(0.dp),
                    ) {
                        mobilityObject.iconUrl?.let { it1 -> RemoteSVGImage(url = it1) }
                        mobilityObject.name?.let { Text(it) }
                    }
                }
            }
        }

        userPosition?.let {
            Marker(
                state = MarkerState(userPosition.toLatLng()),
                onClick = {
                    true
                },
            )
        }
    }
}
