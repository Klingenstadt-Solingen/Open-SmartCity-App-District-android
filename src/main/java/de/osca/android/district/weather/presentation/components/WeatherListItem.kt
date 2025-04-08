package de.osca.android.district.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary

@Composable
fun WeatherListItem(
    name: String,
    distance: Double,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(DistrictDesign.Padding.DEFAULT).padding(start = DistrictDesign.Padding.SMALL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            name,
            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = DistrictDesign.Padding.BIG),
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(DistrictDesign.Size.Icon.BIG),
            )
            val distanceString =
                if (distance < 1000) {
                    if (distance <= 1) {
                        String.format("%.0f", distance * 1000)
                    } else {
                        String.format("%.1f", distance)
                    }
                } else {
                    "+999.9"
                }
            val distanceContentDescription =
                if (distance <= 1) {
                    stringResource(
                        id = R.string.distance_to_km,
                        distanceString,
                    )
                } else {
                    stringResource(
                        id = R.string.distance_to_m,
                        distanceString,
                    )
                }
            Text(
                distanceString + if (distance <= 1) " m" else " km",
                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                modifier =
                    Modifier
                        .clearAndSetSemantics {
                            contentDescription = distanceContentDescription
                        }
                        .width(70.dp),
                textAlign = TextAlign.End,
            )
            Box(modifier = Modifier.size(DistrictDesign.Size.Icon.BIG)) {
                if (selected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check_outline),
                        contentDescription = null,
                        modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                        tint = Color.primary,
                    )
                }
            }
        }
    }
}
