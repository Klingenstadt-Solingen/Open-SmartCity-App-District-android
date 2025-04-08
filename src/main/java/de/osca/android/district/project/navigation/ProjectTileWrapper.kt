package de.osca.android.district.project.navigation

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
import de.osca.android.district.project.viewmodel.ProjectViewModel

object ProjectTileWrapper : DashboardTileWrapper {
    override val position = 3
    override var routeItem: MutableState<NavItem> = mutableStateOf(ProjectNavItems.ProjectNavItem)
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        val projectViewModel: ProjectViewModel = hiltViewModel()
        val districtViewModel: DistrictViewModel = sharedHiltViewModel()
        tileContentDescription.value =
            contentDescriptionFromCountAndName(
                count = projectViewModel.count,
                name = R.string.project_module_title,
            )

        LaunchedEffect(districtViewModel.districtState) {
            projectViewModel.requestCount(districtViewModel.districtState)
        }

        DashboardDefaultTile(
            title = R.string.project_module_title,
            image = R.drawable.ic_cog,
            count = projectViewModel.count,
        )
    }
}
