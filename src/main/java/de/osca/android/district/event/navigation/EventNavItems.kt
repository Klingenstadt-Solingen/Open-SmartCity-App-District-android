package de.osca.android.district.event.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface EventNavItems {
    @Serializable
    data class EventDetailMapNavItem(val objectId: String) : NavItem

    @Serializable
    data class EventDetailNavItem(val objectId: String) : NavItem

    @Serializable
    object EventListNavItem : NavItem
}
