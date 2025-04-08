package de.osca.android.district.project.data.repository

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.project.data.boundary.ProjectContactRepository
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectContact
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectContactRepositoryImpl
    @Inject
    constructor() : ProjectContactRepository {
        override suspend fun getProjectContactsByProjectId(objectId: String): List<ProjectContact> {
            val query =
                ParseObject.createWithoutData(Project::class.java, objectId).contacts.query
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
            return catchParse(query::suspendFind)
        }
    }
