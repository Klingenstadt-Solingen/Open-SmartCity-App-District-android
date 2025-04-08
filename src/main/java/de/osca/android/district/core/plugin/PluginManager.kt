package de.osca.android.district.core.plugin

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PluginManager
    @Inject
    constructor(
        private val plugins: Set<@JvmSuppressWildcards Plugin>,
    ) {
        fun getNavGraphDestinations(): List<NavGraphDestination> {
            return plugins.map { it.navDestinations }
        }

        fun getDashboardWidgets(): List<DashboardWidgetWrapper> {
            return plugins.flatMap { it.dashboardWidgets }.sortedBy { it.position }
        }

        fun getDashboardTiles(): List<DashboardTileWrapper> {
            return plugins.flatMap { it.dashboardTiles }.sortedBy { it.position }
        }
    }
