package de.osca.android.district.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.dashboard.presentation.screen.DashboardScreen
import de.osca.android.district.dashboard.viewmodel.DashboardViewModel

class DashboardDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        /** DASHBOARD */
        composable<DashboardNavItems.DashboardNavItem> {
            val districtViewModel: DistrictViewModel = sharedHiltViewModel()
            val dashboardViewModel: DashboardViewModel = sharedHiltViewModel()

            DashboardScreen(dashboardViewModel, districtViewModel)
        }
    }
}
