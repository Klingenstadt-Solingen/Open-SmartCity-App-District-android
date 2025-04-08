package de.osca.android.district.pointofinterest.data.repository

import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import com.parse.ktx.selectKeys
import com.parse.ktx.whereContainedIn
import com.parse.ktx.whereWithinPolygon
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.core.util.nearDistrict
import de.osca.android.district.pointofinterest.data.boundary.PoiRepository
import de.osca.android.essentials.domain.entity.ParsePOI
import javax.inject.Inject

class PoiRepositoryImpl
    @Inject
    constructor() : PoiRepository {
        @Throws(ApiError::class)
        override suspend fun getPoiById(objectId: String): ParsePOI {
            val query =
                ParseQuery.getQuery(ParsePOI::class.java)
                    .selectKeys(
                        setOf(
                            ParsePOI::name.name,
                            ParsePOI::geopoint.name,
                            ParsePOI::poiCategory.name,
                            ParsePOI::details.name,
                        ),
                    )
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse {
                query.getById(objectId)
            }
        }

        override suspend fun getPois(
            categories: Set<String>,
            districtState: DistrictState,
        ): List<ParsePOI> {
            val query =
                ParseQuery.getQuery(ParsePOI::class.java)
                    .selectKeys(
                        setOf(
                            ParsePOI::name,
                            ParsePOI::geopoint,
                            ParsePOI::poiCategory,
                        ),
                    )
                    .whereContainedIn(ParsePOI::poiCategory, categories.toList())
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .setLimit(5000) // preferably all, realistically no more than 5k

            applyPOIDistrictFilter(query, districtState)

            return try {
                catchParse(query::suspendFind)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

private fun applyPOIDistrictFilter(
    query: ParseQuery<ParsePOI>,
    districtState: DistrictState,
) {
    when (districtState) {
        is DistrictState.DISTRICT -> {
            query.whereWithinPolygon(
                ParsePOI::geopoint,
                districtState.district.area,
            )
        }

        is DistrictState.NEARBY -> {
            query.nearDistrict("geopoint", districtState)
        }

        else -> {}
    }
}
