package de.osca.android.district.project.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.ModuleTitle
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.navigation.ProjectNavItems
import de.osca.android.district.project.presentation.component.ProjectGridItem
import de.osca.android.district.project.presentation.component.ProjectStatusFilter
import de.osca.android.district.project.viewmodel.ProjectGridViewModel
import de.osca.android.district.project.viewmodel.ProjectViewModel

@Composable
fun ProjectGridScreen(
    viewModel: ProjectGridViewModel = hiltViewModel(),
    projectViewModel: ProjectViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.setDistrictState(districtViewModel.districtState)
        projectViewModel.updateWatchedAt(districtViewModel.districtState)
    }

    val projects: LazyPagingItems<Project> = viewModel.projects.collectAsLazyPagingItems()
    val searchText = viewModel.searchText.collectAsState()
    val projectStatus = viewModel.projectStatus.collectAsState()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(projects, isRefreshing)

    TopBarScaffold(searchText.value, { viewModel.setSearchText(it) }) {
        Column {
            ModuleTitle(title = R.string.project_module_title)
            ProjectStatusFilter(
                projectStatus.value,
                statusList = viewModel.statusList,
                setStatus = viewModel::setProjectStatus,
            )
            @OptIn(ExperimentalMaterial3Api::class)
            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = projects::refresh,
            ) {
                LazyVerticalGrid(
                    columns = DistrictDesign.GRID_CELLS_ADAPTIVE,
                    horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
                    modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                    contentPadding = PaddingValues(top = DistrictDesign.Padding.MEDIUM),
                ) {
                    items(count = projects.itemCount) { index ->
                        val project = projects[index]
                        if (project != null) {
                            NavigationLink(
                                navigate =
                                    ProjectNavItems.ProjectDetailsNavItem(project.objectId),
                                elevation = CardDefaults.cardElevation(DistrictDesign.ELEVATION_MEDIUM),
                            ) {
                                ProjectGridItem(project = project)
                            }
                        }
                    }
                    item(span = {
                        GridItemSpan(this.maxLineSpan)
                    }) {
                        Spacer(modifier = Modifier.height(DistrictDesign.Spacing.MEDIUM))
                    }
                    applyPagingHandler(projects)
                }
            }
        }
    }
}
