package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.osca.android.district.core.presentation.component.HtmlText
import de.osca.android.district.core.presentation.component.LinkText
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pointofinterest.data.model.PointOfInterestDetailType

@Composable
fun PoiDetailValue(
    value: String,
    type: PointOfInterestDetailType,
) {
    when (type) {
        PointOfInterestDetailType.TEL -> LinkText(prefix = "tel:", text = value)
        PointOfInterestDetailType.MAIL -> LinkText(prefix = "mailto:", text = value)
        PointOfInterestDetailType.URL -> LinkText(text = value)
        PointOfInterestDetailType.HTML -> HtmlText(htmlString = value)
        else -> Text(value, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
    }
}
