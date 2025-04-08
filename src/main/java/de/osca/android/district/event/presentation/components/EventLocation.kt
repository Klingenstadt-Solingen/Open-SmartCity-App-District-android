package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.event.data.model.EventDetails

@Composable
fun EventLocation(
    eventLocation: EventDetails,
    modifier: Modifier = Modifier,
) {
    val street = eventLocation.streetAdress
    val address =
        "${eventLocation.addressLocality} ${eventLocation.postalCode}"
    val semanticLocation = stringResource(id = R.string.location)

    Row(
        modifier =
            modifier.clearAndSetSemantics {
                contentDescription = "$semanticLocation: $street $address"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT)){
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = "",
            modifier = Modifier.size(DistrictDesign.Size.Icon.BIG),
            tint = Color.primary,
        )
        Column {
            Text(text = street, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
            Text(text = address, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
        }
    }
}
