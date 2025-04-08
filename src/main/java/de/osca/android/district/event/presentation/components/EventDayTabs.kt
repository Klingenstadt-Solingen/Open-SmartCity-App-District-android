package de.osca.android.district.event.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.SelectableTextButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.isSameDay
import de.osca.android.district.event.data.model.EventFilter
import de.osca.android.district.event.viewmodel.EventListViewModel
import java.time.LocalDateTime

val tabs =
    listOf(R.string.tab_button_today, R.string.tab_button_tomorrow, R.string.tab_button_in_2_days)

@Composable
fun EventDayTabs(
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = hiltViewModel(),
) {
    val dates =
        listOf(
            viewModel.selectedRangeMin.collectAsState().value,
            viewModel.selectedRangeMax.collectAsState().value,
        )

    Row(
        modifier =
            modifier
                .padding(top = DistrictDesign.Padding.MEDIUM)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val filterTypes = viewModel.filterTypes.collectAsState()
        Row(
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
        ) {
            @StringRes var tab: Int
            var date: LocalDateTime
            for (index in 0..tabs.lastIndex) {
                tab = tabs[index]
                date = viewModel.getDateOfTab(index)

                SelectableTextButton(
                    id = tab,
                    selected =
                        date.isSameDay(dates) &&
                            filterTypes.value.contains(
                                EventFilter.DAY,
                            ),
                    onClick = {
                        viewModel.changeTab(index)
                    },
                )
            }
        }

        EventDayCalendarButton(
            selected =
                !filterTypes.value.contains(EventFilter.DAY) ||
                    viewModel.checkNoTabIsSelected() ||
                    filterTypes.value.contains(EventFilter.BOOKMARKS),
            onClick = {
                viewModel.showDatePicker = true
            },
        )
    }
}
