package de.osca.android.district.event.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.parse.ParseObject
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.ModuleTitle
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.NavigationLink
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.navigation.EventNavItems
import de.osca.android.district.event.presentation.components.EventDayTabs
import de.osca.android.district.event.presentation.components.EventListItem
import de.osca.android.district.event.presentation.screen.sheet.EventFilterPickerSheet
import de.osca.android.district.event.viewmodel.EventListViewModel
import de.osca.android.district.event.viewmodel.EventViewModel
import kotlinx.datetime.Clock
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    viewModel: EventListViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = sharedHiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.updateDistrict(districtViewModel.districtState)
        eventViewModel.updateWatchedAt(districtViewModel.districtState)
    }

    val modalBottomSheetState = rememberModalBottomSheetState()

    val searchText by viewModel.searchText.collectAsState()

    val events = viewModel.events.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(events, isRefreshing)

    TopBarScaffold(
        searchText = searchText,
        setSearchText = {
            viewModel.updateText(it)
        },
    ) {
        ModuleTitle(title = R.string.event_module_title)
        EventDayTabs(
            modifier =
                Modifier
                    .padding(horizontal = DistrictDesign.Padding.BIGGER)
                    .padding(bottom = DistrictDesign.Padding.SMALL),
        )
        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = events::refresh,
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
                contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
            ) {
                items(events.itemCount) { index ->
                    val event = events[index]

                    if (event != null) {
                        NavigationLink(
                            navigate = EventNavItems.EventDetailNavItem(event.objectId),
                        ) {
                            EventListItem(
                                event = event,
                                eventViewModel.favorites.value.contains(event.objectId)
                            ) {
                                eventViewModel.toggleEvent(event)
                            }
                        }
                    }
                }
                applyPagingHandler(events)
            }
        }
    }

    if (viewModel.showDatePicker) {
        ModalBottomSheet(
            sheetState = modalBottomSheetState,
            onDismissRequest = { viewModel.showDatePicker = false },
            containerColor = Color.White,
        ) {
            EventFilterPickerSheet(
                modifier = Modifier.padding(horizontal = DistrictDesign.Padding.BIGGER),
                viewModel = viewModel,
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.NEXUS_7,
)
@Composable
fun EventContentPreview() {
    ParseObject.registerSubclass(Event::class.java)

    val nowDate = Date(Clock.System.now().toEpochMilliseconds())
    var list: List<Event> = emptyList()
    val event = Event()
    event.name =
        "adfadsf asdfasdfasdfasdfa asdfasdfasdfadsf dfsgdfggdfs  rfdgsfdgs fdsgfdg dsafdsfas"
    event.category = "Ausstellung"
    event.endDate = nowDate
    event.startDate = nowDate
    event.location = HashMap<String, Any>()
    val address = HashMap<String, Any>()
    address["name"] = "Bergische VHS - Solingen - Mummstraße"
    address["streetAddress"] = "Mummstr. 10"
    address["addressLocality"] = "Solingen"
    address["postalCode"] = "42561"
    event.location!!["address"] = address
    list = list.plus(event)

    val event2 = Event()
    event2.name = "adfadsf asd"
    event2.category = "Ausstellung"

    event2.startDate = nowDate
    event2.location = HashMap<String, Any>()
    event2.location!!["address"] = address
    list = list.plus(event2)

    val event3 = Event()
    event3.name = "adfadsf asdfasdfasdfasdfa asdfasdfasdfadsf"
    event3.category = "Ausstellung"
    event3.endDate = nowDate
    event3.startDate = nowDate
    event3.location = HashMap<String, Any>()
    event3.location!!["address"] = address
    list = list.plus(event3)

    val event4 = Event()
    event4.name = "adfadsf asdfasdfasdfasdfa asdfasdfasdfadsf"
    event4.category = "Ausstellung"
    event4.endDate = nowDate
    event4.startDate = nowDate
    event4.location = HashMap<String, Any>()
    event4.location!!["address"] = address
    list = list.plus(event4)

    val event5 = Event()
    event5.name = "adfadsf asdfasdfasdfasdfa asdfasdfasdfadsf"
    event5.category = "Ausstellung"
    event5.endDate = nowDate
    event5.startDate = nowDate
    event5.location = HashMap<String, Any>()
    event5.location!!["address"] = address
}
