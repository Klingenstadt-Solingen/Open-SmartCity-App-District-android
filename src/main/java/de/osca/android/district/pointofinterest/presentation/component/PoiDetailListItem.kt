package de.osca.android.district.pointofinterest.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.pointofinterest.data.model.PointOfInterestDetail

val titleTextStyle = TextStyle(fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE, fontWeight = FontWeight.Bold)
val subtitleTextStyle = TextStyle(fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)

@Composable
fun PoiDetailListItem(
    detail: PointOfInterestDetail,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .semantics(true) {}
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER),
            contentScale = ContentScale.FillBounds,
            model = detail.getIconImageUrl(),
            contentDescription = "",
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                detail.title,
                style = titleTextStyle,
            )
            detail.subtitle?.let {
                Text(
                    detail.subtitle,
                    style = subtitleTextStyle,
                )
            }
            detail.value?.let {
                PoiDetailValue(it, detail.getType())
            }
        }
    }
}
