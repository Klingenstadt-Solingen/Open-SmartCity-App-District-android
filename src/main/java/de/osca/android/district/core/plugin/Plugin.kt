package de.osca.android.district.core.plugin

import de.osca.android.district.core.navigation.NavGraphDestination
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper

interface Plugin {
    val navDestinations: NavGraphDestination
    val dashboardWidgets: List<DashboardWidgetWrapper>
        get() = emptyList()
    val dashboardTiles: List<DashboardTileWrapper>
        get() = emptyList()
}
