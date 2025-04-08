package de.osca.android.district.mobility.data.repository

import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.mobility.data.model.Mobility
import de.osca.android.district.mobility.data.model.MobilityType
import de.osca.android.district.mobility.data.model.MobilityTypeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MobilityRepository {
    @Throws(ApiError::class)
    @GET("mobility/{typeId}")
    suspend fun getMobilityObjects(
        @Path("typeId") type: MobilityType,
        @Query("limit") limit: Int = 50,
        @Query("skip") skip: Int = 0,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("rangeInMeter") rangeInMeter: Double?,
    ): List<Mobility>

    @Throws(ApiError::class)
    @GET("mobility/{type}/{id}")
    suspend fun getMobilityObjectById(
        @Path("type") type: MobilityType,
        @Path("id") id: String,
    ): Mobility

    @Throws(ApiError::class)
    @GET("mobility-type")
    suspend fun getMobilityTypeDto(): List<MobilityTypeDto>
}
