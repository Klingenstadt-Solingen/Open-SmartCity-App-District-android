package de.osca.android.district.project.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectContact
import de.osca.android.district.project.data.model.ProjectPartner
import de.osca.android.district.project.data.model.ProjectPartnerCategory
import de.osca.android.district.project.data.model.ProjectStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectParseObjectRegistration
    @Inject
    constructor() : ParseObjectRegistration {
        override fun registerSubclasses() {
            ParseObject.registerSubclass(Project::class.java)
            ParseObject.registerSubclass(ProjectContact::class.java)
            ParseObject.registerSubclass(ProjectPartner::class.java)
            ParseObject.registerSubclass(ProjectPartnerCategory::class.java)
            ParseObject.registerSubclass(ProjectStatus::class.java)
        }
    }
