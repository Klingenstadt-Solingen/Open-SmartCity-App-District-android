@file:Suppress("ktlint:standard:no-empty-file")

package de.osca.android.district.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.osca.android.district.core.navigation.NavigationGraph
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel

@Composable
fun BootstrapApp(
    navController: NavHostController = rememberNavController(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(Unit) {
        districtViewModel.initFetch()
    }
    LoadingWrapper(
        loadingState = districtViewModel.loadingState,
        retry = {
            districtViewModel.initFetch()
        },
    ) {
        NavigationGraph(
            navController = navController,
        )
    }
}
