package de.osca.android.district.pointofinterest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import de.osca.android.district.R
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.ModuleTitle
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.pointofinterest.presentation.component.PoiMapCategoryFilterIconButton
import de.osca.android.district.pointofinterest.presentation.component.map.MapTitleShape
import de.osca.android.district.pointofinterest.presentation.component.map.PoiMap
import de.osca.android.district.pointofinterest.viewmodel.PoiCategoryViewModel
import de.osca.android.district.pointofinterest.viewmodel.PoiMapViewModel
import de.osca.android.essentials.domain.entity.ParsePOI

/**
 * @param categoryId optional category sourceId to preselect a category
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiMapScreen(
    categoryId: String? = null,
    mapViewModel: PoiMapViewModel = hiltViewModel(),
    categoryViewModel: PoiCategoryViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    var sheetPoi: ParsePOI? by remember { mutableStateOf(null) }
    val detailBottomSheetState = rememberModalBottomSheetState()

    val districtState = districtViewModel.districtState
    val selectedDistrict = (districtState as? DistrictState.DISTRICT)?.district

    val pointsOfInterest = mapViewModel.pointOfInterests

    TopBarScaffold(
        floatingActionButton = {
            PoiMapCategoryFilterIconButton(
                categories = categoryViewModel.categories,
                selectedCategories = categoryViewModel.selectedCategories,
                onClickCategory = { cat, selected ->
                    categoryViewModel.selectCategory(cat, selected)
                },
            )
        },
    ) { paddingValues ->
        LaunchedEffect(categoryId) {
            categoryViewModel.initCategories(categoryId)
        }

        LoadingWrapper(categoryViewModel.loadingState) {
            LaunchedEffect(categoryViewModel.selectedCategories, districtState) {
                mapViewModel.requestPois(
                    categoryViewModel.selectedCategories,
                    districtState = districtState,
                )
            }
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                PoiMap(
                    modifier = Modifier.fillMaxSize(),
                    pointsOfInterest = pointsOfInterest,
                    markerUrl = { poiCategory ->
                        categoryViewModel.getCategory(poiCategory)?.getImageUrl()
                    },
                    onMarkerClick = {
                        sheetPoi = it
                        true
                    },
                    district = selectedDistrict,
                    coroutineScope = mapViewModel.viewModelScope,
                )
                ModuleTitle(
                    title = R.string.poi_module_title,
                    modifier =
                        Modifier
                            .background(Color.White, MapTitleShape(10.dp))
                            .padding(
                                start = DistrictDesign.Padding.MEDIUM,
                                end = DistrictDesign.Padding.BIGGER * 2, // funny shape moment
                                top = DistrictDesign.Padding.BIGGER,
                                bottom = DistrictDesign.Padding.HUGE,
                            ),
                )

                sheetPoi?.let {
                    ModalBottomSheet(
                        sheetState = detailBottomSheetState,
                        onDismissRequest = {
                            sheetPoi = null
                        },
                    ) {
                        PoiDetailScreen(
                            poiObjectId = it.objectId,
                            modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                            poiCategory = categoryViewModel.getCategory(it.poiCategory),
                        )
                    }
                }
            }
        }
    }
}
