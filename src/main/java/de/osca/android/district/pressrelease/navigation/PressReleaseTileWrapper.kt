package de.osca.android.district.pressrelease.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.DashboardDefaultTile
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.util.contentDescriptionFromCountAndName
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.pressrelease.viewmodel.PressReleaseViewModel

object PressReleaseTileWrapper : DashboardTileWrapper {
    override val position = 5
    override var routeItem: MutableState<NavItem> =
        mutableStateOf(PressReleaseNavItems.PressReleaseNavItem)
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        val pressReleaseViewModel: PressReleaseViewModel = hiltViewModel()
        val districtViewModel: DistrictViewModel = sharedHiltViewModel()

        LaunchedEffect(districtViewModel.districtState) {
            pressReleaseViewModel.requestCount(districtViewModel.districtState)
        }

        tileContentDescription.value =
            contentDescriptionFromCountAndName(
                count = pressReleaseViewModel.count,
                name = R.string.press_release_module_title,
            )

        DashboardDefaultTile(
            count = pressReleaseViewModel.count,
            title = R.string.press_release_module_title,
            image = R.drawable.ic_megaphone_with_soundwave,
        )
    }
}
