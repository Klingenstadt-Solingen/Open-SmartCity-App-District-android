package de.osca.android.district.weather.navigation

import de.osca.android.district.core.navigation.NavItem
import kotlinx.serialization.Serializable

sealed interface WeatherNavItems {
    @Serializable
    object WeatherScreenNavItem : NavItem
}
