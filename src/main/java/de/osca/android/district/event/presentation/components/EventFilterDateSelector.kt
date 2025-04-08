package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import de.osca.android.district.core.presentation.component.button.DateButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.formatDayDotMonthNameYear
import java.util.Date

@Composable
fun EventFilterDateSelector(
    text: String,
    date: Date,
    modifier: Modifier,
    semantics: String,
    onDateChanged: (Date?) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text,
            fontSize = DistrictDesign.Size.Font.DEFAULT,
        )

        DateButton(
            date = date,
            onDateChanged = onDateChanged,
            modifier =
                Modifier
                    .clearAndSetSemantics {
                        role = Role.Button
                        contentDescription =
                            "$semantics ${date.formatDayDotMonthNameYear()}"
                    },
        )
    }
}
