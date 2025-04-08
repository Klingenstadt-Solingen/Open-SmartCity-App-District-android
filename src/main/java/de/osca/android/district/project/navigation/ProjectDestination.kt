package de.osca.android.district.project.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.project.presentation.screen.ProjectDetailScreen
import de.osca.android.district.project.presentation.screen.ProjectGridScreen

class ProjectDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        /** Project Grid **/
        composable<ProjectNavItems.ProjectNavItem> {
            ProjectGridScreen()
        }

        /** Project Details **/
        composable<ProjectNavItems.ProjectDetailsNavItem> {
            val objectId = it.toRoute<ProjectNavItems.ProjectDetailsNavItem>().objectId
            ProjectDetailScreen(
                objectId = objectId,
            )
        }
    }
}
