package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.core.util.formatHourMinute
import de.osca.android.district.core.util.toLocalDate
import kotlinx.serialization.json.JsonNull
import java.time.Period
import java.util.Date

@Composable
fun EventDateTime(
    startDate: Date,
    endDate: Date?,
    modifier: Modifier = Modifier,
) {
    val duration =
        endDate?.let { Period.between(startDate.toLocalDate(), it.toLocalDate()).days } ?: 0
    val semanticStart = stringResource(id = R.string.time_from)
    val start = startDate.formatHourMinute()

    Row(
        modifier.clearAndSetSemantics {
            contentDescription = JsonNull.content
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        if (duration < 2) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "",
                modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                tint = Color.primary,
            )
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = start + if (endDate != null) " -" else "",
                    fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                    modifier =
                        Modifier.semantics {
                            contentDescription = "$semanticStart $start"
                        },
                )
                endDate?.let {
                    val semanticEnd = stringResource(id = R.string.time_to)
                    val end = it.formatHourMinute()
                    Text(
                        end,
                        fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                        modifier =
                            Modifier.semantics {
                                contentDescription = "$semanticEnd $end"
                            },
                    )
                }
            }
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "",
                modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                tint = Color.primary,
            )

            Text(
                text = pluralStringResource(id = R.plurals.day, count = duration, duration),
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            )
        }
    }
}
