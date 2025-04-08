package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parse.ParseObject
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.formatDayDotMonthNameYear
import de.osca.android.district.pressrelease.data.model.PressRelease

@Composable
fun PressReleaseListItem(pressRelease: PressRelease) {
    Row(modifier = Modifier.fillMaxWidth().semantics(mergeDescendants = true) {}, horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM)) {
        PressReleaseListItemImage(
            url = pressRelease.imageUrl,
            contentDescription = pressRelease.summary,
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
            modifier = Modifier.padding(DistrictDesign.Padding.MEDIUM),
        ) {
            Text(
                text = pressRelease.date.formatDayDotMonthNameYear(),
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            )
            Text(
                text = pressRelease.title,
                style =
                    defaultTextStyle(
                        DistrictDesign.Size.Font.DEFAULT,
                        fontWeight = FontWeight.Bold,
                    ),
                minLines = 2,
                maxLines = 2,
            )
            ReadingTime(readingTime = pressRelease.readingTime)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PressReleaseListItemPreview() {
    ParseObject.registerSubclass(PressRelease::class.java)
    val pressRelease = PressRelease.dummy()

    PressReleaseListItem(pressRelease)
}
