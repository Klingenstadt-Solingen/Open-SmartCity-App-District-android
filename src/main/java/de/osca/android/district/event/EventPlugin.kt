package de.osca.android.district.event

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.event.navigation.EventDestination
import de.osca.android.district.event.navigation.EventTileWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination =
            EventDestination
        override val dashboardTiles = listOf(EventTileWrapper)
        // override val dashboardWidgets = listOf(EventWidgetWrapper)
    }
