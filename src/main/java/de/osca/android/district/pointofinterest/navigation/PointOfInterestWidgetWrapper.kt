package de.osca.android.district.pointofinterest.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper
import de.osca.android.district.pointofinterest.presentation.component.PoiWidgetView

object PointOfInterestWidgetWrapper : DashboardWidgetWrapper {
    override val position = 1

    @Composable
    override fun ContentView(navController: NavController) {
        PoiWidgetView(navController, modifier = Modifier.height(85.dp))
    }
}
