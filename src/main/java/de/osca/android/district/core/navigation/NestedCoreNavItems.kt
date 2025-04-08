package de.osca.android.district.core.navigation

import kotlinx.serialization.Serializable

sealed interface NestedCoreNavItems {
    @Serializable
    object NestedCoreNavItem : NavItem
}
