package de.osca.android.district.event.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper
import de.osca.android.district.event.presentation.screen.widget.EventWidget

object EventWidgetWrapper : DashboardWidgetWrapper {
    override val position = 3
    @Composable
    override fun ContentView(navController: NavController) {
        EventWidget()
    }
}
