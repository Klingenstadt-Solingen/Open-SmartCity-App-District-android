package de.osca.android.district.pointofinterest.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.pointofinterest.presentation.screen.PoiMapScreen

class PointOfInterestDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        composable<PointOfInterestNavItems.PointOfInterestNavItem> {
            val navItem = it.toRoute<PointOfInterestNavItems.PointOfInterestNavItem>()
            PoiMapScreen(categoryId = navItem.categoryId)
        }
    }
}
