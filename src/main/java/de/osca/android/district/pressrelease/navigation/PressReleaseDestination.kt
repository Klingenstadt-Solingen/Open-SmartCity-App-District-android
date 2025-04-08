package de.osca.android.district.pressrelease.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.pressrelease.presentation.screen.PressReleaseDetailScreen
import de.osca.android.district.pressrelease.presentation.screen.PressReleaseListScreen

class PressReleaseDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        /** DASHBOARD */

        composable<PressReleaseNavItems.PressReleaseNavItem> {
            PressReleaseListScreen()
        }

        composable<PressReleaseNavItems.PressReleaseDetailNavItem> {
            val pressReleaseNavItem = it.toRoute<PressReleaseNavItems.PressReleaseDetailNavItem>()
            PressReleaseDetailScreen(pressReleaseNavItem.objectId)
        }
    }
}
