package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.SelectableTextButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.event.data.model.EventFilter

@Composable
fun EventFilterTimeTabs(
    daySelected: Boolean,
    timeRangeSelected: Boolean,
    modifier: Modifier = Modifier,
    addFilter: (EventFilter) -> Unit,
    removeFilter: (EventFilter) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
    ) {
        SelectableTextButton(
            R.string.day_button,
            selected = daySelected,
            onClick = {
                addFilter(EventFilter.DAY)
                removeFilter(EventFilter.TIMERANGE)
            },
            modifier =
                Modifier
                    .wrapContentSize(),
        )

        SelectableTextButton(
            R.string.timerange_button,
            selected = timeRangeSelected,
            onClick = {
                addFilter(EventFilter.TIMERANGE)
                removeFilter(EventFilter.DAY)
            },
            modifier =
                Modifier
                    .wrapContentSize(),
        )

        SelectableTextButton(
            R.string.all_time_button,
            selected =
                !daySelected && !timeRangeSelected,
            onClick = {
                removeFilter(EventFilter.DAY)
                removeFilter(EventFilter.TIMERANGE)
            },
            modifier =
                Modifier
                    .wrapContentSize(),
        )
    }
}
