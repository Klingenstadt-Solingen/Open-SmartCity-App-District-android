package de.osca.android.district.mobility.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface MobilityNavItems {
    @Serializable
    object MobilityMapNavItem : NavItem

    @Serializable
    class MobilityVehicleDetailNavItem(val id: String) : NavItem

    @Serializable
    class MobilityStationDetailNavItem(val id: String) : NavItem
}
