package de.osca.android.district.core.data.model.district

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParsePolygon
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute
import kotlinx.serialization.Serializable

@Serializable
@ParseClassName("District")
class District : ParseObject() {
    val name: String by stringAttribute()
    val area: ParsePolygon by attribute()
    val logo: ParseFile by attribute()
    val image: ParseFile by attribute()

    val serialArea: List<Pair<Double,Double>>
        get() =
            area.coordinates
                .map { p ->
                    Pair(p.latitude,p.longitude)
                }

    val areaPolygon: List<LatLng>
        get() =
            area.coordinates
                .map { p ->
                    LatLng(
                        p.latitude,
                        p.longitude,
                    )
                }.toList()

    val latLngBounds: LatLngBounds
        get() {
            val centerBuilder = LatLngBounds.builder()
            for (point in areaPolygon) {
                centerBuilder.include(point)
            }
            return centerBuilder.build()
        }
}
