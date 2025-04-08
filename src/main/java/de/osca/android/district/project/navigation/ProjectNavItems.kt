package de.osca.android.district.project.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface ProjectNavItems {
    @Serializable
    object ProjectNavItem : NavItem

    @Serializable
    class ProjectDetailsNavItem(val objectId: String) : NavItem
}
