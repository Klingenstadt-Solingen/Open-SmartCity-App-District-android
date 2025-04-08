package de.osca.android.district.pressrelease.data.boundaries

import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.pressrelease.data.model.PressRelease
import java.util.Date

interface PressReleaseRepository {
    @Throws(ApiError::class)
    suspend fun getPressReleaseById(objectId: String): PressRelease

    @Throws(ApiError::class)
    suspend fun getPressReleases(
        districtState: DistrictState = DistrictState.ALL,
        searchText: String? = null,
        skip: Int = 0,
        limit: Int = 5,
    ): List<PressRelease>

    suspend fun getNewPressReleaseCount(
        watchedAt: Date,
        districtState: DistrictState,
    ): Int
}
