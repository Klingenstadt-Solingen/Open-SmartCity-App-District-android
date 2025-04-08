package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.formatDayDotMonthNameYear
import de.osca.android.district.event.data.model.Event

@Composable
fun EventDetailHead(
    modifier: Modifier = Modifier,
    event: Event,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)) {
            Text(
                event.startDate.formatDayDotMonthNameYear(),
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            )
            Text(
                text = event.name,
                style =
                    defaultTextStyle(
                        DistrictDesign.Size.Font.HEADLINE,
                        fontWeight = FontWeight.Bold,
                    ),
                maxLines = 3,
            )
            Text(text = event.category, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            EventInfo(
                event = event,
            )
        }
    }
}
