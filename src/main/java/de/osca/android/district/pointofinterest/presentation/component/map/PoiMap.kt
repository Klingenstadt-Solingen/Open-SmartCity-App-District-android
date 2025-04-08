package de.osca.android.district.pointofinterest.presentation.component.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.presentation.design.linkColor
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.event.data.repository.MarkerIconCache
import de.osca.android.essentials.domain.entity.ParsePOI
import kotlinx.coroutines.CoroutineScope

@Composable
fun PoiMap(
    modifier: Modifier = Modifier,
    pointsOfInterest: List<ParsePOI> = emptyList(),
    district: District? = null,
    initLatLng: LatLng = LatLng(51.170975, 7.083238),
    initZoom: Float = 13f,
    onMarkerClick: (ParsePOI) -> Boolean = { false },
    markerUrl: ((String) -> String?) = { null },
    coroutineScope: CoroutineScope,
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    val cameraPosition =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(initLatLng, initZoom)
        }

    LaunchedEffect(district) {
        district?.latLngBounds?.let { latLngBounds ->
            cameraPosition.animate(CameraUpdateFactory.newLatLngBounds(latLngBounds, 16))
        }
    }

    val context = LocalContext.current
    val markerCache = remember { MarkerIconCache(context, coroutineScope) }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        uiSettings = mapUiSettings,
        properties = MapProperties(isMyLocationEnabled = districtViewModel.districtState is DistrictState.NEARBY),
    ) {
        TileOverlay(tileProvider = PoiTileOverlay(), zIndex = -100F)

        district?.let {
            DistrictPolygon(district = district)
        }

        TileOverlay(tileProvider = PoiTileOverlay())

        val districtState = districtViewModel.districtState
        if (districtState is DistrictState.NEARBY && districtState.maxDistance != NEARBY_MAX_DISTANCE) {
            Circle(
                center = districtState.location,
                fillColor = Color.linkColor.copy(alpha = 0.3f),
                radius = districtState.maxDistance.toDouble() * 1000,
                strokeColor = Color.linkColor.copy(alpha = 0.8f),
                strokeWidth = 5f,
                zIndex = 9f,
            )
        }

        pointsOfInterest.forEach { poi ->
            val url = markerUrl(poi.poiCategory)
            if (url?.isNotBlank() == true) {
                val markerIcon = markerCache.getMarkerFlow(url).collectAsState()

                Marker(
                    icon = markerIcon.value,
                    state = MarkerState(poi.geopoint ?: initLatLng),
                    onClick = {
                        onMarkerClick(poi)
                    },
                    contentDescription = "${poi.poiCategory}: ${poi.name}",
                    zIndex = 10F,
                )
            }
        }
    }
}

private val mapUiSettings =
    MapUiSettings(
        compassEnabled = true,
        indoorLevelPickerEnabled = false,
        mapToolbarEnabled = false,
        myLocationButtonEnabled = true,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true,
        scrollGesturesEnabledDuringRotateOrZoom = true,
        tiltGesturesEnabled = true,
        zoomControlsEnabled = false,
        zoomGesturesEnabled = true,
    )
