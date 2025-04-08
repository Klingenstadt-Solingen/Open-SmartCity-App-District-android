package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.presentation.design.primary

@Composable
fun CounterCircle(
    count: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {


    Badge(
        modifier =
            modifier.size(28.dp), // unique size
        contentColor = Color.primary,
        containerColor = Color.accentColor,
    ) {
        if (count <= 99) {
            Text(
                text = count.toString(),
                style = defaultTextStyle(DistrictDesign.Size.Font.DEFAULT, fontWeight = FontWeight.Bold),
            )
        } // ELSE NO NUMBER IS SHOWN AS INTENDED
    }
}

@Preview
@Composable
fun CounterCirclePreview() {
    CounterCircle(count = 18)
}
