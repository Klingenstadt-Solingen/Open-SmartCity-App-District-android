package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.formatMonthYear
import de.osca.android.district.project.data.model.Project
import java.util.Date

@Composable
fun ProjectInfo(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val startDate = project.startDate
    val endDate = project.endDate

    Row(
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ProjectStatus(status = project.status)

        if (startDate != null) {
            ProjectDate(startDate = startDate, endDate = endDate)
        }
    }
}

@Composable
fun getSemanticDate(
    startDate: Date,
    endDate: Date?,
): String {
    val start = startDate.formatMonthYear()
    val end = endDate?.formatMonthYear() ?: ""

    val semanticPlanned = stringResource(id = R.string.planned)
    val semanticUntil = stringResource(id = R.string.until_date)
    val semanticOpenEnd = stringResource(id = R.string.open_end)

    var content = "$semanticPlanned $start "
    if (endDate != null) {
        content += "$semanticUntil $end"
    } else {
        content += semanticOpenEnd
    }

    return content
}
