package de.osca.android.district.weather.data.repository

import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendFind
import com.parse.ktx.whereEqualTo
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.core.util.applyDistrictFilter
import de.osca.android.district.weather.data.boundaries.WeatherRepository
import de.osca.android.district.weather.data.model.Weather

class WeatherRepositoryImpl : WeatherRepository {
    @Throws(ApiError::class)
    override suspend fun getWeatherById(objectId: String): Weather {
        val query =
            ParseQuery
                .getQuery(Weather::class.java)
                .setMaxCacheAge(CACHE_TIME)
                .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
        return catchParse { query.getById(objectId) }
    }

    @Throws(ApiError::class)
    override suspend fun getWeathers(
        districtState: DistrictState,
        skip: Int,
        limit: Int,
    ): List<Weather> {
        val query =
            ParseQuery
                .getQuery(Weather::class.java)
                .whereEqualTo(Weather::maintenance, false)

        query.applyDistrictFilter(districtState)
        query.skip = skip
        query.limit = limit
        query
            .setMaxCacheAge(CACHE_TIME)
            .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
        return catchParse(query::suspendFind)
    }
}
