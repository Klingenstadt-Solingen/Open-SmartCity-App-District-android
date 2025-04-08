package de.osca.android.district.politics.data.repository
import de.osca.android.district.politics.data.model.PoliticAgendaItem
import retrofit2.http.GET
import retrofit2.http.Query

interface AgendaRepository {
    @GET("/api/v1/agenda-items/{id}")
    suspend fun getById(id: String): PoliticAgendaItem
    
    @GET("/api/v1/agenda-items")
    suspend fun findAllBy(@Query("page") page: Int,
                          @Query("size") size: Int,
                          @Query("meetingId") meetingId: String): List<PoliticAgendaItem>
}
