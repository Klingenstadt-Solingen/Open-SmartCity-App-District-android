package de.osca.android.district.core.data.model.district

import com.google.android.gms.maps.model.LatLng
import de.osca.android.district.core.NEARBY_MAX_DISTANCE

sealed class DistrictState {
    data object ALL : DistrictState()

    data class DISTRICT(
        val district: District,
    ) : DistrictState()

    // Nearby is sorted by current location or current location district
    data class NEARBY(
        val location: LatLng,
        val maxDistance: Float = NEARBY_MAX_DISTANCE,
    ) : DistrictState()
}
