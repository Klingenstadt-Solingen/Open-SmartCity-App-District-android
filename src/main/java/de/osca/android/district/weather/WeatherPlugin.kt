package de.osca.android.district.weather

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.weather.navigation.WeatherDestination
import de.osca.android.district.weather.navigation.WeatherTileWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = WeatherDestination()
        override val dashboardTiles = listOf(WeatherTileWrapper)
    }
