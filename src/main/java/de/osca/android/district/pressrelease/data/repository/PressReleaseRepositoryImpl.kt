package de.osca.android.district.pressrelease.data.repository

import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendCount
import com.parse.coroutines.suspendFind
import com.parse.ktx.orderByDescending
import com.parse.ktx.whereContains
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.core.util.applyDistrictFilter
import de.osca.android.district.pressrelease.data.boundaries.PressReleaseRepository
import de.osca.android.district.pressrelease.data.model.PressRelease
import java.util.Date
import javax.inject.Inject

class PressReleaseRepositoryImpl
    @Inject
    constructor() : PressReleaseRepository {
        override suspend fun getPressReleases(
            districtState: DistrictState,
            searchText: String?,
            skip: Int,
            limit: Int,
        ): List<PressRelease> {
            val query = ParseQuery.getQuery(PressRelease::class.java)

            if (!searchText.isNullOrBlank()) {
                query.whereContains(PressRelease::title, searchText)
            }

            query.orderByDescending(PressRelease::date)

            query.applyDistrictFilter(districtState)

            query.skip = skip
            query.limit = limit

            query
                .setMaxCacheAge(CACHE_TIME)
                .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse(query::suspendFind)
        }

        @Throws(ApiError::class)
        override suspend fun getPressReleaseById(objectId: String): PressRelease {
            val query =
                ParseQuery.getQuery(PressRelease::class.java)
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse { query.getById(objectId) }
        }

        override suspend fun getNewPressReleaseCount(
            watchedAt: Date,
            districtState: DistrictState,
        ): Int {
            val query =
                ParseQuery.getQuery(PressRelease::class.java)
            query.whereGreaterThan("updatedAt", watchedAt)

            query.applyDistrictFilter(districtState)

            return try {
                query.suspendCount()
            } catch (_: ParseException) {
                0
            }
        }
    }
