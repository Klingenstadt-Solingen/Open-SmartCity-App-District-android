package de.osca.android.district.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface DashboardWidgetWrapper {
    val position: Int

    @Composable
    fun ContentView(navController: NavController)
}
