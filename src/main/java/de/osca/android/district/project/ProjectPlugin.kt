package de.osca.android.district.project

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.project.navigation.ProjectDestination
import de.osca.android.district.project.navigation.ProjectTileWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = ProjectDestination()
        override val dashboardTiles = listOf(ProjectTileWrapper)
    }
