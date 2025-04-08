package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import de.osca.android.district.R
import de.osca.android.district.core.PRIMARY_COLOR
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.project.data.model.ProjectStatus

@Composable
fun ProjectStatus(status: ProjectStatus?) {
    val semanticStatus = stringResource(id = R.string.status)
    val statusTitle =
        status?.let { stringResource(id = it.getStringResource()) }
            ?: stringResource(R.string.unknown_status)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        modifier =
            Modifier.clearAndSetSemantics {
                contentDescription = "$semanticStatus: $statusTitle"
            },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cog),
            contentDescription = "",
            tint = PRIMARY_COLOR,
            modifier = Modifier.height(DistrictDesign.Size.Icon.BIG),
        )
        Text(
            text = statusTitle,
            style = defaultTextStyle(DistrictDesign.Size.Font.SMALL_TEXT),
        )
    }
}
