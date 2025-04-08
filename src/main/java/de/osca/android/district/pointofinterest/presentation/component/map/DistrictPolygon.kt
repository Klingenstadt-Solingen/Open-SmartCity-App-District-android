package de.osca.android.district.pointofinterest.presentation.component.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Polygon
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.data.model.district.District

@Composable
@GoogleMapComposable
fun DistrictPolygon(district: District) {
    Polygon(
        points = district.areaPolygon,
        fillColor = Color.Transparent,
        strokeColor = PRIMARY_COLOR,
        zIndex = 10F
    )
}
