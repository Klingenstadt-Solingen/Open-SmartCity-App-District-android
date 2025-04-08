package de.osca.android.district.pointofinterest.data.boundary

import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.essentials.domain.entity.ParsePOI

interface PoiRepository {
    @Throws(ApiError::class)
    suspend fun getPoiById(objectId: String): ParsePOI

    @Throws(ApiError::class)
    suspend fun getPois(
        categories: Set<String>,
        districtState: DistrictState,
    ): List<ParsePOI>
}
