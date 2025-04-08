package de.osca.android.district.core.data.repository

import com.parse.ParseQuery
import com.parse.coroutines.first
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.boundaries.DistrictRepository
import de.osca.android.district.core.data.model.district.District
import javax.inject.Inject

class DistrictRepositoryImpl
    @Inject
    constructor() : DistrictRepository {
        override suspend fun getDistricts(): List<District> {
            val query =
                ParseQuery.getQuery(District::class.java)
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse(query::suspendFind)
        }

        override suspend fun getDistrictById(districtObjectId: String): District {
            val query =
                ParseQuery.getQuery(District::class.java)
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse { query.getById(districtObjectId) }
        }

        override suspend fun getFirstDistrict(): District {
            val query =
                ParseQuery.getQuery(District::class.java)
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse { query.first() }
        }
    }
