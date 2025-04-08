package de.osca.android.district.event.navigation

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
import de.osca.android.district.event.viewmodel.EventViewModel

object EventTileWrapper : DashboardTileWrapper {
    override val position = 4
    override var routeItem: MutableState<NavItem> = mutableStateOf(EventNavItems.EventListNavItem)
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        val eventViewModel: EventViewModel = hiltViewModel()
        val districtViewModel: DistrictViewModel = sharedHiltViewModel()

        LaunchedEffect(districtViewModel.districtState) {
            eventViewModel.requestCount(districtViewModel.districtState)
        }

        tileContentDescription.value =
            contentDescriptionFromCountAndName(
                count = eventViewModel.count,
                name = R.string.event_module_title,
            )

        DashboardDefaultTile(
            title = R.string.event_module_title,
            image = R.drawable.ic_confetti,
            count = eventViewModel.count,
        )
    }
}
