package de.osca.android.district.mobility

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.mobility.navigation.MobilityDestination
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MobilityPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination =
            MobilityDestination

        // disabled for now
        override val dashboardTiles = emptyList<DashboardTileWrapper>() // listOf(MobilityTileWrapper)
    }
