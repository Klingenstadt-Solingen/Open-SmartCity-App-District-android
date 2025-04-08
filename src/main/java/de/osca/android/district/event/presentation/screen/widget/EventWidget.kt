package de.osca.android.district.event.presentation.screen.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.event.navigation.EventNavItems
import de.osca.android.district.event.presentation.components.EventListItem
import de.osca.android.district.event.viewmodel.EventViewModel
import de.osca.android.district.event.viewmodel.EventWidgetViewModel

@Composable
fun EventWidget(
    modifier: Modifier = Modifier,
    viewModel: EventWidgetViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = sharedHiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel()
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.requestNextEvent(districtViewModel.districtState)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        LoadingWrapper(viewModel.loadingState) { events ->
            if (events.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.no_event_found),
                        fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                ) {
                    events.forEach { event ->
                        NavigationLink(EventNavItems.EventDetailNavItem(objectId = event.objectId)) {
                            EventListItem(
                                event = event,
                                eventViewModel.favorites.value.contains(event.objectId),
                            ) {
                                eventViewModel.toggleEvent(event)
                            }
                        }
                    }
                }
            }
        }
    }
}