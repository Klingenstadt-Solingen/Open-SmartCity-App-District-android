package de.osca.android.district.mobility.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.core.presentation.design.DistrictDesign


@Composable
fun MobilityListItem(
    iconUrl: String?,
    name: String,
    details: String,
    entfernung: String
) {
    Row(
        Modifier
            .fillMaxWidth().padding(vertical = DistrictDesign.Padding.BIG).padding(end = DistrictDesign.Padding.BIG),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.2F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            iconUrl?.let {
                RemoteSVGImage(it, modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER).aspectRatio(1f))
            }
        }
        Column(
            Modifier
                .fillMaxWidth(0.7F)
        ) {
            Text(name, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT, fontWeight = FontWeight.Bold)
            Text(details, fontSize = DistrictDesign.Size.Font.SMALL_TEXT)
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text(entfernung)
        }
    }

}
