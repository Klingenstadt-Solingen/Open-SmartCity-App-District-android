package de.osca.android.district.politics.data.repository

import de.osca.android.district.politics.data.model.PoliticMembership
import retrofit2.http.GET
import retrofit2.http.Query

interface MembershipRepository {
    @GET("/api/v1/memberships/{id}")
    suspend fun getById(id: String): PoliticMembership
    
    @GET("/api/v1/memberships")
    suspend fun findAllBy(@Query("page") page: Int,
                          @Query("size") size: Int,
                          @Query("organizationId") organizationId: String?,
                          @Query("personId") personId: String? = null
    )
    : List<PoliticMembership>
}
