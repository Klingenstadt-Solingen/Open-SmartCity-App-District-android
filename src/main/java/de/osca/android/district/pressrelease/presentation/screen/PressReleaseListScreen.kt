package de.osca.android.district.pressrelease.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import de.osca.android.district.pressrelease.navigation.PressReleaseNavItems
import de.osca.android.district.pressrelease.presentation.component.PressReleaseListItem
import de.osca.android.district.pressrelease.viewmodel.PressReleaseListViewModel
import de.osca.android.district.pressrelease.viewmodel.PressReleaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PressReleaseListScreen(
    viewModel: PressReleaseListViewModel = hiltViewModel(),
    pressReleaseViewModel: PressReleaseViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.updateDistrict(districtViewModel.districtState)
        pressReleaseViewModel.updateWatchedAt(districtViewModel.districtState)
    }
    val searchText by viewModel.searchText.collectAsState()

    val pressReleases = viewModel.pressReleases.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(pressReleases, isRefreshing)

    TopBarScaffold(searchText = searchText, setSearchText = { viewModel.updateSearch(it) }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        ) {
            ModuleTitle(title = R.string.press_release_module_title)
            @OptIn(ExperimentalMaterial3Api::class)
            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = pressReleases::refresh,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = DistrictDesign.Padding.BIGGER),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                    contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
                ) {
                    items(pressReleases.itemCount) { index ->
                        pressReleases[index]?.let { pressRelease ->
                            NavigationLink(
                                navigate =
                                    PressReleaseNavItems.PressReleaseDetailNavItem(pressRelease.objectId),
                            ) {
                                PressReleaseListItem(
                                    pressRelease = pressRelease,
                                )
                            }
                        }
                    }
                    applyPagingHandler(pressReleases)
                }
            }
        }
    }
}
