package de.osca.android.district.dashboard.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.DashboardDefaultTile
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun DashboardTileGrid(
    modifier: Modifier = Modifier,
    tiles: List<DashboardTileWrapper>,
    paddingValues: PaddingValues = PaddingValues(
        start = DistrictDesign.Padding.BIG,
        top = DistrictDesign.Padding.SMALL,
        end = DistrictDesign.Padding.BIG,
        bottom = DistrictDesign.Padding.MEDIUM),
) {

    LazyVerticalGrid(
        columns = DistrictDesign.GRID_CELLS_ADAPTIVE,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        contentPadding = paddingValues,
        modifier = modifier,
    ) {
        items(tiles) {
            it.Button(Modifier.aspectRatio(1.35f))
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun DashboardTileGridPreview() {
    class PreviewNavItem : NavItem

    class PreviewDashboardTileWrapper : DashboardTileWrapper {
        override val position = 0
        override var routeItem: MutableState<NavItem> = mutableStateOf(PreviewNavItem())
        override val tileContentDescription: MutableState<String> = mutableStateOf("")

        @Composable
        override fun ContentView() {
            DashboardDefaultTile(
                title = R.string.dashboard_city_name,
                image = R.drawable.ic_left,
                count = 18,
            )
        }
    }

    val tiles =
        listOf(
            PreviewDashboardTileWrapper(),
            PreviewDashboardTileWrapper(),
            PreviewDashboardTileWrapper(),
            PreviewDashboardTileWrapper(),
            PreviewDashboardTileWrapper(),
        )
    DashboardTileGrid(
        modifier =
            Modifier.padding(
                PaddingValues(
                    vertical = 20.dp,
                    horizontal = 10.dp,
                ),
            ),
        tiles,
    )
}
