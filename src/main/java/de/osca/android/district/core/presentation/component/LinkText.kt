package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.util.startLinkIntent

@Composable
fun LinkText(
    modifier: Modifier = Modifier,
    style: TextStyle =
        defaultTextStyle(
            DistrictDesign.Size.Font.NORMAL_TEXT,
            fontColor = Color.Blue,
        ),
    prefix: String = "",
    text: String,
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
        modifier =
            modifier.clickable {
                startLinkIntent(prefix + text, context)
            },
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "",
            modifier =
                Modifier.size(
                    DistrictDesign.Size.Icon.SMALL,
                ),
        )
        Text(
            style = style,
            text = text,
            maxLines = 1,
        )
    }
}
