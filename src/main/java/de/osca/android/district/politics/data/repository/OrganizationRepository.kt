package de.osca.android.district.politics.data.repository
import de.osca.android.district.politics.data.model.PoliticOrganization
import retrofit2.http.GET
import retrofit2.http.Query

interface OrganizationRepository {
    @GET("/api/v1/organisations/{id}")
    suspend fun getById(@Query("id") id: String): PoliticOrganization
    
    @GET("/api/v1/organisations")
    suspend fun findAllBy(@Query("page") page: Int,
                          @Query("size") size: Int,
                          @Query("districtId") districtId: String): List<PoliticOrganization>
}
