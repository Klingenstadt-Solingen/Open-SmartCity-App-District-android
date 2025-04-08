package de.osca.android.district.pressrelease.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface PressReleaseNavItems {
    @Serializable
    object PressReleaseNavItem : NavItem

    @Serializable
    data class PressReleaseDetailNavItem(val objectId: String) : NavItem
}
