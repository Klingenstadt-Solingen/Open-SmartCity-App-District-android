package de.osca.android.district.pointofinterest.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface PointOfInterestNavItems {
    @Serializable
    data class PointOfInterestNavItem(
        val categoryId: String?,
    ) : NavItem
}
