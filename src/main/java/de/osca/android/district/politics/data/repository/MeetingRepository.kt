package de.osca.android.district.politics.data.repository
import de.osca.android.district.politics.data.model.PoliticMeeting
import kotlinx.datetime.LocalDateTime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeetingRepository {
    @GET("/api/v1/meetings/{id}")
    suspend fun getById(@Path("id") organizationId: String): PoliticMeeting
    
    @GET("/api/v1/meetings")
    suspend fun findAllBy(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("organizationId") organizationId: String?
    ): List<PoliticMeeting>

    @GET("/api/v1/meetings")
    suspend fun findAllTill(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("organizationId") organizationId: String?,
        @Query("endDateTime") endDateTime: LocalDateTime?
    ): List<PoliticMeeting>
}
