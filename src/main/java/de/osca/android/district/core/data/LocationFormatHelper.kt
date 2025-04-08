package de.osca.android.district.core.data

import com.google.android.gms.maps.model.LatLng
import com.parse.ParseGeoPoint

fun LatLng.toParseGeoPoint(): ParseGeoPoint {
    return ParseGeoPoint(latitude, longitude)
}

fun ParseGeoPoint.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}
