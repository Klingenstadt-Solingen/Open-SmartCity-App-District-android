package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.formatDayDotMonthNameYear
import de.osca.android.district.core.util.formatHourMinute
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventDetails

@Composable
fun EventListItem(
    event: Event,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavorite: (Event) -> Unit,
) {
    val details = EventDetails(event)
    val address = "${details.streetAdress} \n${details.addressLocality} ${details.postalCode}"
    val content = getSemanticsText(event, address)

    Box(
        modifier =
            modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(DistrictDesign.Padding.MEDIUM)
                    .clearAndSetSemantics { contentDescription = content }
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.SMALL),
                modifier = Modifier.weight(1f),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM)) {
                    Text(
                        event.startDate.formatDayDotMonthNameYear(),
                        fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                        maxLines = 1,
                    )
                    event.type?.let { type ->
                        Text(
                            type.name,
                            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Text(
                    text = event.name,
                    fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    minLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = event.category,
                    fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                    maxLines = 1,
                )
                EventInfo(event = event)
            }
            EventListItemImage(
                url = event.image,
                modifier =
                    Modifier
                        .size(100.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
            )
        }
        FavoriteButton(
            modifier,
            isFavorite = isFavorite,
            iconPadding = DistrictDesign.Padding.SMALL,
            onClick = {
                onFavorite(event)
            }
        )
    }
}

@Composable
private fun getSemanticsText(
    event: Event,
    address: String,
): String {
    val semanticsDateStart = stringResource(id = R.string.date_from)
    val semanticsTimeStart = stringResource(id = R.string.time_from)
    val semanticsTimeTo = stringResource(id = R.string.time_to)
    val semanticsEventType = stringResource(id = R.string.event_type)
    val semanticsLocation = stringResource(id = R.string.location)

    val start = event.startDate.formatDayDotMonthNameYear()

    val content =
        "$semanticsDateStart: $start \n${event.name}\n$semanticsEventType: ${event.category}"

    val startTime = event.startDate.formatHourMinute()
    val endTime = event.endDate?.formatHourMinute()

    var contentTime = "$semanticsTimeStart $startTime"

    if (endTime != null) {
        contentTime += "$semanticsTimeTo $endTime"
    }

    val contentLocation = "$semanticsLocation $address"

    return "$content \n$contentTime \n$contentLocation"
}
