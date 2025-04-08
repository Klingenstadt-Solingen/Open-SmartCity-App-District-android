package de.osca.android.district.project.data.repository

import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.coroutines.getById
import com.parse.coroutines.suspendCount
import com.parse.coroutines.suspendFind
import com.parse.ktx.include
import com.parse.ktx.whereContains
import com.parse.ktx.whereEqualTo
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.core.util.applyDistrictFilter
import de.osca.android.district.project.data.boundary.ProjectRepository
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectStatus
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepositoryImpl
    @Inject
    constructor() : ProjectRepository {
        override suspend fun getProjects(
            projectStatus: ProjectStatus?,
            skip: Int,
            limit: Int,
            searchText: String,
            districtState: DistrictState,
        ): List<Project> {
            var query =
                ParseQuery.getQuery(Project::class.java)
            if (searchText.isNotBlank()) {
                query =
                    ParseQuery.or(
                        listOf(
                            query.whereContains(Project::name, searchText),
                            ParseQuery
                                .getQuery(Project::class.java)
                                .whereContains(Project::summary, searchText),
                        ),
                    )
            }
            if (projectStatus != null) {
                query.whereEqualTo(Project::status, projectStatus.objectId)
            }

            query.applyDistrictFilter(districtState)

            query
                .setSkip(skip)
                .setLimit(limit)
                .include(Project::status)
                .setMaxCacheAge(CACHE_TIME)
                .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse(query::suspendFind)
        }

        override suspend fun getProjectStatusList(): List<ProjectStatus> {
            val query = ParseQuery.getQuery(ProjectStatus::class.java)

            return catchParse(query::suspendFind)
        }

        override suspend fun getProjectById(objectId: String): Project {
            val query =
                ParseQuery
                    .getQuery(Project::class.java)
                    .include(Project::status)
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse { query.getById(objectId) }
        }

        override suspend fun getNewProjectCount(
            watchedAt: Date,
            districtState: DistrictState,
        ): Int {
            val query =
                ParseQuery.getQuery(Project::class.java)
            query.whereGreaterThan("updatedAt", watchedAt)

            query.applyDistrictFilter(districtState)

            return try {
                query.suspendCount()
            } catch (_: ParseException) {
                0
            }
        }
    }
