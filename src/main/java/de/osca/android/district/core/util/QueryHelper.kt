package de.osca.android.district.core.util

import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.data.model.district.DistrictState
import kotlin.reflect.KProperty

fun <T : ParseObject> ParseQuery<T>.nearDistrict(
    key: KProperty<Any?>,
    districtState: DistrictState.NEARBY,
) = nearDistrict(key.name, districtState)

fun <T : ParseObject> ParseQuery<T>.nearDistrict(
    key: String,
    districtState: DistrictState.NEARBY,
) {
    val geoPoint = ParseGeoPoint(districtState.location.latitude, districtState.location.longitude)
    val maxDistance = districtState.maxDistance
    if (maxDistance < NEARBY_MAX_DISTANCE) {
        this.whereWithinKilometers(key, geoPoint, maxDistance.toDouble())
    } else {
        this.whereNear(key, geoPoint)
    }
}

fun <T : ParseObject> ParseQuery<T>.applyDistrictFilter(districtState: DistrictState) {
    when (districtState) {
        is DistrictState.ALL -> {}
        is DistrictState.DISTRICT ->
            this.whereEqualTo("districts", districtState.district)

        is DistrictState.NEARBY ->
            this.nearDistrict("geopoint", districtState)
    }
}
