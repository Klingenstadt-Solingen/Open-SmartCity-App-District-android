package de.osca.android.district.pointofinterest.data.model.delegate

import com.google.android.gms.maps.model.LatLng
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import kotlin.reflect.KProperty

class LatLngParseDelegate(private val name: String?) {
    operator fun getValue(
        parseObject: ParseObject,
        property: KProperty<*>,
    ): LatLng? {
        val geoPoint = parseObject.getParseGeoPoint(name ?: property.name)
        if (geoPoint != null) {
            return LatLng(geoPoint.latitude, geoPoint.longitude)
        }
        return null
    }

    operator fun setValue(
        parseObject: ParseObject,
        property: KProperty<*>,
        value: LatLng,
    ) {
        parseObject.put(name ?: property.name, ParseGeoPoint(value.latitude, value.longitude))
    }
}

inline fun latLngAttribute(name: String? = null) = LatLngParseDelegate(name)
