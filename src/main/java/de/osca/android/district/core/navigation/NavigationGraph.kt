package de.osca.android.district.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import de.osca.android.district.core.viewmodel.NavigationViewModel
import de.osca.android.district.dashboard.navigation.DashboardNavItems

typealias NavigationEnterTransition = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
typealias NavigationExitTransition = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
typealias NavigationPopEnterTransition = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
typealias NavigationPopExitTransition = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
typealias NavigationSizeTransform = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    enterTransition: NavigationEnterTransition = {
        fadeIn(animationSpec = tween(350)).plus(
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700),
            ),
        )
    },
    exitTransition: NavigationExitTransition = {
        fadeOut(animationSpec = tween(700))
    },
    popEnterTransition: NavigationPopEnterTransition = {
        fadeIn(animationSpec = tween(700))
    },
    popExitTransition: NavigationPopExitTransition = {
        fadeOut(animationSpec = tween(350)).plus(
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700),
            ),
        )
    },
    sizeTransform: NavigationSizeTransform? = null,
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    // TODO: Abstract startDestination set
    startDestination: NavItem = DashboardNavItems.DashboardNavItem,
) {
    CompositionLocalProvider(LocalNavigationController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            sizeTransform = sizeTransform,
        ) {
            navigationViewModel.getNavGraphDestinations().forEach {
                it.callDestination(this)
            }
        }
    }
}

fun NavGraphBuilder.districtNestedGraph(
    navigationViewModel: NavigationViewModel,
    enterTransition: NavigationEnterTransition = {
        fadeIn(animationSpec = tween(350)).plus(
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700),
            ),
        )
    },
    exitTransition: NavigationExitTransition = {
        fadeOut(animationSpec = tween(700))
    },
    popEnterTransition: NavigationPopEnterTransition = {
        fadeIn(animationSpec = tween(700))
    },
    popExitTransition: NavigationPopExitTransition = {
        fadeOut(animationSpec = tween(350)).plus(
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700),
            ),
        )
    },
    sizeTransform: NavigationSizeTransform? = null,
    startDestination: NavItem = DashboardNavItems.DashboardNavItem,
) {
    navigation<NestedCoreNavItems.NestedCoreNavItem>(
        startDestination = startDestination,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        sizeTransform = sizeTransform,
    ) {
        navigationViewModel.getNavGraphDestinations().forEach {
            it.callDestination(this)
        }
    }
}
