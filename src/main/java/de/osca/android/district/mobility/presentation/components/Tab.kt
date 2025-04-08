package de.osca.android.district.mobility.presentation.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary


@Composable
fun Tabs(
    selectedState: MutableState<Int> = mutableStateOf<Int>(0),
    border: BorderStroke? = null,
    tabs: List<@Composable (selectedState: MutableState<Int>,
                            index: Int,size: Float)-> Unit> = emptyList(),
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        colors = CardColors(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray),
        shape = DistrictDesign.ROUNDED_SHAPE,
        border = border,
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(0.dp),
            verticalAlignment = Alignment.CenterVertically) {
            tabs.forEachIndexed { index, tab ->
                tab(selectedState, index, (1.0F / (tabs.size - index)))
            }
        }
    }
}


@Composable
fun Tabs(
    iconUrlList: List<String> = emptyList(),
    selectedState: MutableState<Int> = mutableStateOf<Int>(0),
    border: BorderStroke? = null,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        colors = CardColors(Color.LightGray, Color.LightGray, Color.LightGray, Color.LightGray),
        shape = DistrictDesign.ROUNDED_SHAPE,
        border = border,
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(DistrictDesign.Padding.SMALL),
            verticalAlignment = Alignment.CenterVertically) {
            iconUrlList.forEachIndexed { index, url ->
                Tab(url, selectedState, index, (1.0F / (iconUrlList.size - index)))
                if(!((selectedState.value - index) == 1 || selectedState.value == index || index == iconUrlList.lastIndex)) VerticalDivider(modifier = Modifier.height(DistrictDesign.Size.Icon.BIGGER / 2), thickness = 3.dp, Color.Gray)
            }
        }

    }
}




@Composable
fun Tab(
    url: String,
    selectedState: MutableState<Int>,
    index: Int,
    size: Float
    ) {

    val elevation =
        CardDefaults.cardElevation(
            defaultElevation = DistrictDesign.ELEVATION_SMALL,
            pressedElevation = DistrictDesign.ELEVATION_SMALL,
        )

    val unselectedColor = CardColors(Color.LightGray, Color.Black, Color.White, Color.Black)
    val selectedColor = CardColors(Color.White, Color.Black, Color.White, Color.Black)

    val color =
        if (selectedState.value == index) {
            selectedColor
        } else {
            unselectedColor
        }

    Card(
        modifier = Modifier.semantics {
            this.role = Role.RadioButton
            this.selected = selectedState.value == index
        }.fillMaxWidth(size).height(75.dp),
        elevation = if (selectedState.value == index) {
            elevation
        } else {
            CardDefaults.cardElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
            )
        },
        onClick = {
            selectedState.value = index
        },
        colors = color,
        shape = DistrictDesign.ROUNDED_SHAPE,
        content = {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                RemoteSVGImage(url = url, color = Color.primary)
            }
        },
    )
}
