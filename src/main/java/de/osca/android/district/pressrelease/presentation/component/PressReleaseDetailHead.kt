package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.parse.ParseObject
import de.osca.android.district.core.presentation.component.button.ShareButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.util.formatDayDotMonthNameYear
import de.osca.android.district.pressrelease.data.model.PressRelease

@Composable
fun PressReleaseDetailHead(pressRelease: PressRelease) {
    Column(verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)) {
        Text(
            text = pressRelease.date.formatDayDotMonthNameYear(),
            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
        )
        Text(
            text = pressRelease.title,
            style =
                TextStyle(
                    fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                    fontWeight = FontWeight.Bold,
                ),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ReadingTime(readingTime = pressRelease.readingTime)
            if (pressRelease.url?.isNotBlank() == true) {
                ShareButton(pressRelease.url)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressReleaseDetailScreenHeadPreview() {
    ParseObject.registerSubclass(PressRelease::class.java)
    val pressRelease = PressRelease.dummy()

    PressReleaseDetailHead(pressRelease)
}
