package de.osca.android.district.pointofinterest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.DashboardDefaultTile
import de.osca.android.district.core.presentation.component.DashboardTileWrapper

object PointOfInterestTileWrapper : DashboardTileWrapper {
    override val position = 6
    override var routeItem: MutableState<NavItem> =
        mutableStateOf(PointOfInterestNavItems.PointOfInterestNavItem(null))
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        tileContentDescription.value = stringResource(id = R.string.poi_module_title)

        DashboardDefaultTile(
            image = R.drawable.ic_map_with_marker,
            title = R.string.poi_module_title,
            count = 0,
        )
    }
}
