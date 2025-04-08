package de.osca.android.district.core.data.boundaries

import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.repository.ApiError

interface DistrictRepository {
    @Throws(ApiError::class)
    suspend fun getDistricts(): List<District>

    @Throws(ApiError::class)
    suspend fun getDistrictById(districtObjectId: String): District

    @Throws(ApiError::class)
    suspend fun getFirstDistrict(): District
}
