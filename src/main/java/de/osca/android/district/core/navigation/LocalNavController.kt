package de.osca.android.district.core.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavigationController =
    compositionLocalOf<NavController> { error("No Navigation Controller found!") }
