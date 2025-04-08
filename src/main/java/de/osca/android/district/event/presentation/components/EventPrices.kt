package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.event.data.model.EventPrice

@Composable
fun EventPrices(
    prices: List<EventPrice>,
    modifier: Modifier = Modifier,
) {
    Column(modifier, horizontalAlignment = Alignment.Start) {
        Text(
            stringResource(R.string.event_prices),
            fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
            fontWeight = FontWeight.Bold,
            modifier =
            Modifier
                .padding(bottom = DistrictDesign.Spacing.DEFAULT),
        )
        for (p in prices) {
            Row(horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG)) {
                Text(text = p.name, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
                Text(text = "${p.price} ${p.priceCurrency}", fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
            }
        }
    }
}
