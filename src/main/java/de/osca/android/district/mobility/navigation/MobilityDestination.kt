package de.osca.android.district.mobility.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.mobility.presentation.screen.MobilityMapScreen
import de.osca.android.district.mobility.presentation.screen.MobilityStationDetailScreen
import de.osca.android.district.mobility.presentation.screen.MobilityVehicleDetailScreen

object MobilityDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {

        composable<MobilityNavItems.MobilityMapNavItem> {
            MobilityMapScreen()
        }
        composable<MobilityNavItems.MobilityVehicleDetailNavItem> {
            val id = it.toRoute<MobilityNavItems.MobilityVehicleDetailNavItem>().id
            MobilityVehicleDetailScreen(id)
        }
        composable<MobilityNavItems.MobilityStationDetailNavItem> {
            val id = it.toRoute<MobilityNavItems.MobilityStationDetailNavItem>().id
            MobilityStationDetailScreen(id)
        }
    }
}
