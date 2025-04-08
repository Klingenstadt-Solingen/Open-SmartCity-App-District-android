package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.core.presentation.component.button.ShareButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.project.data.model.Project

@Composable
fun ProjectDetailHead(project: Project) {
    Text(
        text = project.name,
        style =
            defaultTextStyle(
                fontSize = DistrictDesign.Size.Font.HEADLINE,
                fontWeight = FontWeight.Bold,
                fontColor = Color.Black,
            ),
        modifier = Modifier.padding(bottom = DistrictDesign.Padding.MEDIUM),
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        ProjectInfo(project = project)
        if (project.url?.isNotBlank() == true) {
            ShareButton(project.url)
        }
    }
}
