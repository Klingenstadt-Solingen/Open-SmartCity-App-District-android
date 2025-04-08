package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import de.osca.android.district.R
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.formatMonthYear
import java.util.Date

@Composable
fun ProjectDate(
    startDate: Date,
    endDate: Date?,
) {
    val description = getSemanticDate(startDate, endDate)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        modifier =
            Modifier.clearAndSetSemantics {
                contentDescription = description
            },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "cogs",
            tint = PRIMARY_COLOR,
            modifier = Modifier.height(DistrictDesign.Size.Icon.BIG),
        )
        Column {
            Text(
                text = startDate.formatMonthYear(),
                style = defaultTextStyle(DistrictDesign.Size.Font.SMALL_TEXT),
            )
            if (endDate != null) {
                Text(
                    text = endDate.formatMonthYear(),
                    style = defaultTextStyle(DistrictDesign.Size.Font.SMALL_TEXT),
                )
            }
        }
    }
}
