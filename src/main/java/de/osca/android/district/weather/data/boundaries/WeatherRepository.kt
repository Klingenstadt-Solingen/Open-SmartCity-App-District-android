package de.osca.android.district.weather.data.boundaries

import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.weather.data.model.Weather

interface WeatherRepository {
    @Throws(ApiError::class)
    suspend fun getWeatherById(objectId: String): Weather

    @Throws(ApiError::class)
    suspend fun getWeathers(
        districtState: DistrictState,
        skip: Int = 0,
        limit: Int = 50,
    ): List<Weather>
}
