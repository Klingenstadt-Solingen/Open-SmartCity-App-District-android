package de.osca.android.district.project.data.boundary

import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectStatus
import java.util.Date

interface ProjectRepository {
    @Throws(ApiError::class)
    suspend fun getProjectById(objectId: String): Project

    @Throws(ApiError::class)
    suspend fun getProjects(
        projectStatus: ProjectStatus?,
        skip: Int,
        limit: Int,
        searchText: String,
        districtState: DistrictState,
    ): List<Project>

    @Throws(ApiError::class)
    suspend fun getProjectStatusList(): List<ProjectStatus>

    suspend fun getNewProjectCount(
        watchedAt: Date,
        districtState: DistrictState,
    ): Int
}
