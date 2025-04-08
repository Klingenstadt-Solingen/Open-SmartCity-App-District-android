package de.osca.android.district.dashboard.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface DashboardNavItems {
    @Serializable
    object DashboardNavItem : NavItem
}
