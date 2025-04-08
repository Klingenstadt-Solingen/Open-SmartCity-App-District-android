package de.osca.android.district.politics

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.politics.navigation.PoliticsDestination
import de.osca.android.district.politics.navigation.PoliticsTileWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PoliticsPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = PoliticsDestination()
        override val dashboardTiles = listOf(PoliticsTileWrapper)
    }
