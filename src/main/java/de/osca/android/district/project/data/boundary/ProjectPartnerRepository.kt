package de.osca.android.district.project.data.boundary

import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.project.data.model.ProjectPartner

interface ProjectPartnerRepository {
    @Throws(ApiError::class)
    suspend fun getProjectPartnersByProjectId(objectId: String): List<ProjectPartner>
}
