package de.osca.android.district.weather.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.weather.presentation.screen.WeatherScreen

class WeatherDestination : NavGraphDestination {
    override fun NavGraphBuilder.destination() {
        /** Weather Screen */
        composable<WeatherNavItems.WeatherScreenNavItem> {
            WeatherScreen()
        }
    }
}
