package de.osca.android.district.dashboard

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.dashboard.navigation.DashboardDestination
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardPlugin
    @Inject
    constructor() : Plugin {
        override val navDestinations: NavGraphDestination = DashboardDestination()
    }
