package de.osca.android.district.core.presentation.component

import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.viewinterop.AndroidView
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun HtmlText(
    htmlString: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = DistrictDesign.Size.Font.NORMAL_TEXT,
) {
    val spannedText =
        remember {
            Html.fromHtml(
                htmlString,
                Html.FROM_HTML_MODE_LEGACY,
            )
        }

    AndroidView(
        factory = {
            TextView(it).apply {
                autoLinkMask = Linkify.WEB_URLS + Linkify.EMAIL_ADDRESSES
                linksClickable = true
                setBackgroundColor(Color.Transparent.toArgb())
                setTextColor(Color.Black.toArgb())
                setLinkTextColor(Color.Blue.toArgb())
                movementMethod = ScrollingMovementMethod()
                textSize = fontSize.value
            }
        },
        update = {
            it.text = spannedText
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun HtmlTextPreview() {
    HtmlText(htmlString = "<span>Test</span>")
}
