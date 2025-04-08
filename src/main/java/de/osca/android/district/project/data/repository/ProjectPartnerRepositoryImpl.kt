package de.osca.android.district.project.data.repository

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import com.parse.ktx.include
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.project.data.boundary.ProjectPartnerRepository
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectPartner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectPartnerRepositoryImpl
    @Inject
    constructor() : ProjectPartnerRepository {
        override suspend fun getProjectPartnersByProjectId(objectId: String): List<ProjectPartner> {
            val query =
                ParseObject
                    .createWithoutData(Project::class.java, objectId)
                    .partners.query
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)
                    .include(ProjectPartner::category)

            return catchParse(query::suspendFind)
        }
    }
