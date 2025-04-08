package de.osca.android.district.project.data.boundary

import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.project.data.model.ProjectContact

interface ProjectContactRepository {
    @Throws(ApiError::class)
    suspend fun getProjectContactsByProjectId(objectId: String): List<ProjectContact>
}
