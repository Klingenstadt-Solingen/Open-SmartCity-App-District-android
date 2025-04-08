package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun ReadingTime(readingTime: Int) {
    val readingTimeString = stringResource(id = R.string.reading_time)
    val minString = stringResource(id = R.string.minutes)

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_timer),
            contentDescription = "",
            Modifier.size(DistrictDesign.Size.Icon.BIG),
        )
        Text(
            "$readingTime $minString $readingTimeString",
            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            modifier = Modifier.padding(start = DistrictDesign.Padding.DEFAULT),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingTimeTextPreview() {
    ReadingTime(20)
}
