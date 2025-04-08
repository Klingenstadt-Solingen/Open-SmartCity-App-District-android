package de.osca.android.district.mobility.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary

@Composable
fun MobilityDefaultItem(){

    val last = remember { mutableStateListOf<String>() }
    val favorite = remember { mutableStateListOf<String>() }

    Column(Modifier
        .fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth()) {
                Text("Zuletzt")
                HorizontalDivider()
                if(last.size > 0){
                    for(text in last.subList(0,if (last.lastIndex < 4) last.lastIndex else 4)) {
                        DefaultSearchRow(text)
                    }
                }
            }
        }
        /*
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth()) {
                Text("Favorite")
                HorizontalDivider()
                if(last.size > 0){
                    for(text in last.subList(0,if (last.lastIndex < 4) last.lastIndex else 4)) {
                        DefaultSearchRow(mobilityObject)
                    }
                }
            }
        }
        */
    }

}
@Composable
fun DefaultSearchRow(text: String, iconUrl: String? = null){
    Row(Modifier
        .fillMaxWidth().padding(DistrictDesign.Padding.DEFAULT),
        verticalAlignment = Alignment.CenterVertically) {
        Column  {
            Icon(
                painter = painterResource(id = R.drawable.ic_magnifying_glass),
                contentDescription = null,
                modifier = Modifier.size(DistrictDesign.Size.Icon.BIG)
            )
        }
        Column(Modifier.fillMaxWidth(0.8F),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center) {
            Text(text)
        }
        Column(Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.End)  {
            iconUrl?.let {
                RemoteSVGImage(url= it,Modifier.size(DistrictDesign.Size.Icon.BIG), Color.primary)
            }
        }
    }
    HorizontalDivider()
}