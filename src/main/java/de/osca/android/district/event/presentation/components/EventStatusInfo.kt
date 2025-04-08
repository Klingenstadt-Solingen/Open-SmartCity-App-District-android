package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun EventStatusInfo(
    status: String,
    imageResource: Int,
    crossedOut: Boolean = false,
    color: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = imageResource),
                contentDescription = "",
                modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
                tint = color,
            )
            if (crossedOut) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = "",
                    modifier = Modifier.size(35.dp),
                    tint = color,
                )
            }
        }
        Text(
            text = status,
            fontSize = DistrictDesign.Size.Font.HEADLINE,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}
