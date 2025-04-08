package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.district.core.SECONDARY_COLOR
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.pointofinterest.presentation.component.map.PoiTileOverlay
import de.osca.android.essentials.domain.entity.Coordinates

@ExperimentalMaterial3Api
@Composable
fun Map(
    onClick: () -> Unit = {},
    onMarkerClick: (objectId: String) -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth().height(300.dp),
    mapSettings: MapUiSettings = MapUiSettings(
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
    eventLocation: Coordinates,
    userPosition: Coordinates? = null,
    eventBooths: List<EventBooth>? = null,
) {
    val cameraPosition =
        rememberCameraPositionState {
            position = CameraPosition(eventLocation.toLatLng(), 15.0f, 0f, 0f)
        }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        uiSettings = mapSettings,
        onMapClick = {
            onClick()
        },
        onMapLoaded = {
            // ...
        },
    ) {
        TileOverlay(tileProvider = PoiTileOverlay(), zIndex = -100F)

        eventBooths?.forEach { eventBooth ->
            eventBooth.type?.icon?.url?.let { url ->
                val painter =
                    rememberAsyncImagePainter(
                        model =
                        ImageRequest.Builder(LocalContext.current)
                            .decoderFactory(SvgDecoder.Factory())
                            .data(url)
                            .size(130)
                            .build(),
                    )
                MarkerComposable(
                    title = eventBooth.name,
                    state = MarkerState(LatLng(eventBooth.geopoint.latitude, eventBooth.geopoint.longitude)),
                    onClick = {
                        onMarkerClick(eventBooth.objectId)
                        true
                    },
                    contentDescription = "",
                    zIndex = 100F
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(0.dp)){
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                        )
                        Text(eventBooth.name)
                    }
                }
            }
        }

        eventBooths?.forEach { eventBooth ->
            Polygon(
                points = eventBooth.area.coordinates.map { parseGeoPoint ->  LatLng(parseGeoPoint.latitude, parseGeoPoint.longitude) },
                fillColor = SECONDARY_COLOR,
                strokeColor = SECONDARY_COLOR,
                zIndex = 10F
            )
        }

        userPosition?.let {
            Marker(
                state = MarkerState(userPosition.toLatLng()),
                onClick = {
                    true
                },
            )
        }

        Marker(
            state = MarkerState(eventLocation.toLatLng()),
            onClick = {
                true
            },
        )
    }
}