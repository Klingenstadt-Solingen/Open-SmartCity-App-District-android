package de.osca.android.district.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.osca.android.district.R
import de.osca.android.district.core.navigation.LocalNavigationController
import de.osca.android.district.event.navigation.EventNavItems
import de.osca.android.district.event.presentation.screen.widget.EventWidget
import de.osca.android.essentials.presentation.component.design.BaseListContainer
import de.osca.android.essentials.presentation.component.design.MasterDesignArgs

@Composable
fun DistrictEventWidget(
    navController: NavController,
    districtWidgetViewModel: DistrictWidgetViewModel = hiltViewModel(),
    masterDesignArgs: MasterDesignArgs = districtWidgetViewModel.defaultDesignArgs,
) {
    BaseListContainer(
        text =  stringResource(
            id = R.string.big_event_title
        ),
        showMoreOption = masterDesignArgs.vWidgetShowMoreOption,
        moduleDesignArgs = DistrictModuleDesignArgs(),
        onMoreOptionClick = {
            navController.navigate(EventNavItems.EventListNavItem)
        },
        masterDesignArgs = masterDesignArgs,
    ) {
        CompositionLocalProvider(LocalNavigationController provides navController) {
            EventWidget()
        }
    }
}
