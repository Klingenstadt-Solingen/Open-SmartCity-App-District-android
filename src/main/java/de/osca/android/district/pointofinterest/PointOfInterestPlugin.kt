package de.osca.android.district.pointofinterest

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.pointofinterest.navigation.PointOfInterestDestination
import de.osca.android.district.pointofinterest.navigation.PointOfInterestTileWrapper
import de.osca.android.district.pointofinterest.navigation.PointOfInterestWidgetWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointOfInterestPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = PointOfInterestDestination()
        override val dashboardTiles = listOf(PointOfInterestTileWrapper)
        override val dashboardWidgets = listOf(PointOfInterestWidgetWrapper)
    }
