package de.osca.android.district.event.presentation.screen.sheet

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.toDate
import de.osca.android.district.core.util.toLocalDateTime
import de.osca.android.district.event.data.model.EventFilter
import de.osca.android.district.event.presentation.components.EventFilterDateSelector
import de.osca.android.district.event.presentation.components.EventFilterFavoriteSwitch
import de.osca.android.district.event.presentation.components.EventFilterTimeTabs
import de.osca.android.district.event.viewmodel.EventListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFilterPickerSheet(
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel,
) {
    val filterTypes = viewModel.filterTypes.collectAsState()
    val semanticsFrom = stringResource(id = R.string.date_from)
    val semanticsTo = stringResource(id = R.string.date_to)

    val headlineTranslation =
        pluralStringResource(
            id = R.plurals.date_timerange,
            count = if (filterTypes.value.contains(EventFilter.DAY)) 1 else 0,
        )
    val dateTranslation = stringResource(id = R.string.date)
    val dateFromTranslation = stringResource(id = R.string.from_date).capitalize(Locale.current)
    val dateUntilTranslation = stringResource(id = R.string.until_date).capitalize(Locale.current)

    val startDate = viewModel.selectedRangeMin.collectAsState()
    val endDate = viewModel.selectedRangeMax.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
    ) {
        Row {
            EventFilterTimeTabs(
                filterTypes.value.contains(EventFilter.DAY),
                filterTypes.value.contains(EventFilter.TIMERANGE),
                Modifier.weight(1f)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = DistrictDesign.Padding.BIG),
                addFilter = { viewModel.addFilter(it) },
                removeFilter = { viewModel.removeFilter(it) },
            )
            GeneralButton(
                onClick = {
                    viewModel.addFilter(EventFilter.DAY)
                    viewModel.removeFilter(EventFilter.TIMERANGE)
                    viewModel.removeFilter(EventFilter.BOOKMARKS)
                    viewModel.changeTab(0)
                },
                contentPadding = PaddingValues(DistrictDesign.Padding.DEFAULT),
                modifier =
                    Modifier
                        .padding(horizontal = DistrictDesign.Padding.BIG),
                content = {
                    Icon(
                        painter =
                            painterResource(
                                id = R.drawable.ic_circle_cross_filled,
                            ),
                        contentDescription = stringResource(id = R.string.apply),
                        modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                        tint = Color.Red,
                    )
                },
            )
        }
        Text(
            headlineTranslation,
            style =
                defaultTextStyle(
                    DistrictDesign.Size.Font.SUB_SUB_TITLE,
                    fontWeight = FontWeight.Bold,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DistrictDesign.Padding.BIG),
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = DistrictDesign.Padding.BIG),
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EventFilterDateSelector(
                if (filterTypes.value.contains(EventFilter.DAY)) dateTranslation else dateFromTranslation,
                startDate.value.toDate(),
                Modifier.weight(1f),
                semanticsFrom,
            ) { date ->
                date?.toLocalDateTime()?.let {
                    if (filterTypes.value.contains(EventFilter.DAY)) {
                        viewModel.selectDate(it)
                    } else {
                        if (it > endDate.value) {
                            viewModel.selectRange(it, it)
                        } else {
                            viewModel.selectRange(it, endDate.value)
                        }
                    }
                }
            }

            if (filterTypes.value.contains(EventFilter.TIMERANGE)) {
                EventFilterDateSelector(
                    dateUntilTranslation,
                    endDate.value.toDate(),
                    Modifier.weight(1f),
                    semanticsTo,
                ) { date ->
                    date?.toLocalDateTime()?.let {
                        if (it < startDate.value) {
                            viewModel.selectRange(it, it)
                        } else {
                            viewModel.selectRange(startDate.value, it)
                        }
                    }
                }
            }
        }
        EventFilterFavoriteSwitch(
            filterTypes.value.contains(EventFilter.BOOKMARKS),
            Modifier
                .fillMaxWidth()
                .padding(horizontal = DistrictDesign.Padding.BIG),
        ) {
            if (it) {
                viewModel.addFilter(EventFilter.BOOKMARKS)
            } else {
                viewModel.removeFilter(EventFilter.BOOKMARKS)
            }
        }
    }
}
