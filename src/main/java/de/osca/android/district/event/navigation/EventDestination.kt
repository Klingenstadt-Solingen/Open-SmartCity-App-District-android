package de.osca.android.district.event.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.event.presentation.screen.EventDetailMapScreen
import de.osca.android.district.event.presentation.screen.EventDetailScreen
import de.osca.android.district.event.presentation.screen.EventListScreen

object EventDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        composable<EventNavItems.EventListNavItem> {
            EventListScreen()
        }

        composable<EventNavItems.EventDetailNavItem> {
            val navItem = it.toRoute<EventNavItems.EventDetailNavItem>()

            EventDetailScreen(
                objectId = navItem.objectId,
            )
        }

        composable<EventNavItems.EventDetailMapNavItem> {
            val navItem = it.toRoute<EventNavItems.EventDetailMapNavItem>()

            EventDetailMapScreen(
                objectId = navItem.objectId,
            )
        }
    }
}
