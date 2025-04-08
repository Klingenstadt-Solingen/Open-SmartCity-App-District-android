package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.FadingEdges
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pointofinterest.navigation.PointOfInterestNavItems
import de.osca.android.district.pointofinterest.viewmodel.PoiCategoryViewModel

@Composable
fun PoiWidgetView(
    navController: NavController,
    modifier: Modifier = Modifier,
    poiCategoryViewModel: PoiCategoryViewModel = hiltViewModel(),
) {
    val semanticsQuickOpen = stringResource(id = R.string.quick_open)

    LaunchedEffect(Unit) {
        poiCategoryViewModel.requestCategories()
    }
    Box(modifier = modifier) {
        LoadingWrapper(loadingState = poiCategoryViewModel.loadingState) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
                ) {
                    items(poiCategoryViewModel.categories) { category ->
                        PoiCategoryItemButton(
                            category = category,
                            signalizeSelected = false,
                            modifier =
                                Modifier.semantics {
                                    onClick("$semanticsQuickOpen ${category.name}", null)
                                },
                        ) {
                            navController.navigate(
                                PointOfInterestNavItems.PointOfInterestNavItem(
                                    categoryId = category.sourceId,
                                ),
                            )
                        }
                    }
                }
                FadingEdges(edges = 15.dp)
            }
        }
    }
}
