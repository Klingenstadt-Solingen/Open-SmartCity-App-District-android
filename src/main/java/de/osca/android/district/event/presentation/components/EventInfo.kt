package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventDetails

@Composable
fun EventInfo(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        when (event.eventStatus) {
            "canceled" -> {
                EventStatusInfo(
                    stringResource(id = R.string.event_canceled),
                    R.drawable.ic_calendar,
                    true,
                    Color.Red,
                )
            }

            else -> {
                EventDateTime(event.startDate, event.endDate)
                EventLocation(EventDetails(event))
            }
        }
    }
}
