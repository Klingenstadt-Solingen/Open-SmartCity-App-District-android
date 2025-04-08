package de.osca.android.district.mobility.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.button.SelectableButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.mobility.data.model.BusStation
import de.osca.android.district.mobility.data.model.Mobility
import de.osca.android.district.mobility.data.model.Vehicle
import de.osca.android.district.mobility.viewmodel.MobilityMapViewModel

@Composable
fun MobilitySearchSheet(selectedMobility: Mobility?) {
    val mobilityMapViewModel: MobilityMapViewModel = hiltViewModel()

    val searchText = remember { mutableStateOf("") }

    val searchFocus = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(DistrictDesign.Padding.BIGGER),
        verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIGGER),
    ) {
        Box(Modifier.fillMaxWidth().semantics { isTraversalGroup = true }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    DistrictSearchTextField(
                        text = searchText.value,
                        onValueChange = {
                            searchFocus.value = false
                            searchText.value = it
                        },
                        onTrailingIcon = {
                            searchFocus.value = false
                            searchText.value = ""
                        },
                        searchFocus,
                    )
                }
                if (searchFocus.value) {
                    Row {
                        MobilityDefaultItem()
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.5F)) {
                Text(
                    "ausgewählter Startort Adresse",
                    style =
                        defaultTextStyle(
                            DistrictDesign.Size.Font.SUB_SUB_TITLE,
                            Color.primary,
                            fontWeight = FontWeight.Bold,
                        ),
                    maxLines = 2,
                )
            }
            Column(modifier = Modifier.fillMaxWidth(0.5F), horizontalAlignment = Alignment.End) {
                SelectableButton(
                    onClick = {},
                    content = {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_navigation),
                                contentDescription = "",
                                tint = Color.primary,
                                modifier =
                                    Modifier
                                        .size(DistrictDesign.Size.Icon.BIG),
                            )
                            Text(
                                text = "Route",
                                maxLines = 1,
                                style =
                                    defaultTextStyle(
                                        DistrictDesign.Size.Font.SUB_SUB_TITLE,
                                        Color.primary,
                                        fontWeight = FontWeight.Normal,
                                    ),
                            )
                        }
                    },
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val selectedState = remember { mutableIntStateOf(0) }
            Column {
                Tabs(
                    iconUrlList = mobilityMapViewModel.mobilityTypes.map { it.iconUrl },
                    selectedState = selectedState,
                )
            }
        }
        LoadingWrapper(loadingState = mobilityMapViewModel.loadingState) { mobilityObjects ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
            ) {
                items(
                    items = mobilityObjects,
                    itemContent = { mobilityObject ->
                        when (mobilityObject) {
                            is Vehicle -> VehicleListItem(mobilityObject)
                            is BusStation -> StationListItem(mobilityObject)
                            else -> {}
                        }
                    },
                )
            }
        }
    }
}
