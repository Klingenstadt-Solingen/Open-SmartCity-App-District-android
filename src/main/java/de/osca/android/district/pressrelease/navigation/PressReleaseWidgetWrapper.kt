package de.osca.android.district.pressrelease.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.osca.android.district.core.presentation.component.DashboardWidgetWrapper
import de.osca.android.district.pressrelease.presentation.component.PressReleaseWidget

object PressReleaseWidgetWrapper : DashboardWidgetWrapper {
    override val position = 0

    @Composable
    override fun ContentView(navController: NavController) {
        PressReleaseWidget(modifier = Modifier.height(85.dp))
    }
}
