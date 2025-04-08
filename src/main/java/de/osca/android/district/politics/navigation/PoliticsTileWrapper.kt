package de.osca.android.district.politics.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import de.osca.android.district.R
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.DashboardDefaultTile
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.util.contentDescriptionFromCountAndName
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PoliticsTileWrapper : DashboardTileWrapper {
    override val position = 3
    override var routeItem: MutableState<NavItem> = mutableStateOf(PoliticsNavItems.PoliticsNavItem)
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        val districtViewModel: DistrictViewModel = sharedHiltViewModel()

        if (districtViewModel.districtState is DistrictState.DISTRICT) {
            (districtViewModel.districtState as? DistrictState.DISTRICT)?.let {
                routeItem.value =
                    PoliticsNavItems.PoliticsDetailsNavItem(
                        it.district.objectId,
                        it.district.logo.url,
                        Json.encodeToString(it.district.serialArea),
                    )
            }
        } else {
            routeItem.value = PoliticsNavItems.PoliticsNavItem
        }

        tileContentDescription.value =
            contentDescriptionFromCountAndName(
                count = 0,
                name = R.string.politics_module_title,
            )

        DashboardDefaultTile(
            title = R.string.politics_module_title,
            image = R.drawable.ic_users,
            count = 0,
        )
    }
}
