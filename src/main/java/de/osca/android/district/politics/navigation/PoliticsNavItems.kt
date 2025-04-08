package de.osca.android.district.politics.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface PoliticsNavItems {
    @Serializable
    object PoliticsNavItem : NavItem

    @Serializable
    class PoliticsDetailsNavItem(val districtId: String, val logoUrl: String, val serialArea: String) : NavItem

    @Serializable
    class PoliticsMembersNavItem(val organizationId: String, val organizationTitle: String) : NavItem

    @Serializable
    class PoliticsMemberDetailNavItem(val membership: String) : NavItem

    @Serializable
    class PoliticsMeetingsNavItem(val organizationId: String, val organizationTitle: String) : NavItem

    @Serializable
    class PoliticsMeetingDetailNavItem(val meeting: String) : NavItem

}
