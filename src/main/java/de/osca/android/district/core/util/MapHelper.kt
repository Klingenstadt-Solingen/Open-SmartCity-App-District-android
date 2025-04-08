package de.osca.android.district.core.util

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

fun getBoundsLatLong(points: List<LatLng>, defaultLatLng: LatLng): CameraUpdate {
    if (points.isEmpty()) {
        return CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12.0f)
    }

    val builder = LatLngBounds.Builder()
    for (point in points) {
        builder.include(point)
    }
    val bounds = builder.build()

    return CameraUpdateFactory.newLatLngBounds(bounds, 400)
}
