package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.formatHourMinute
import de.osca.android.district.core.util.formatWeekday
import de.osca.android.district.event.viewmodel.EventDetailViewModel

@Composable
fun EventOpeningHours(
    modifier: Modifier = Modifier,
    viewModel: EventDetailViewModel = hiltViewModel(),
) {
    if (viewModel.eventOpeningHours.isNotEmpty()) {
        Column(
            modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                stringResource(R.string.event_openinghours),
                fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                fontWeight = FontWeight.Bold,
                modifier =
                Modifier
                    .padding(bottom = DistrictDesign.Spacing.DEFAULT),
            )
            if (viewModel.eventOpeningHours.isEmpty()) {
                Text(
                    text = stringResource(R.string.event_no_openinghours)
                )
            } else {
                viewModel.eventOpeningHours.forEach { eventOpeningHour ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
                    ) {
                        Text(
                            text = "${eventOpeningHour.startTime.formatWeekday()}:",
                            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                        )
                        Text(
                            text = stringResource(id = R.string.event_from_to, eventOpeningHour.startTime.formatHourMinute(), eventOpeningHour.endTime.formatHourMinute()),
                            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                        )
                    }
                }
            }
        }
    }
}
