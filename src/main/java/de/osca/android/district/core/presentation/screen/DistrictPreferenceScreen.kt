package de.osca.android.district.core.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.NEARBY_MIN_DISTANCE
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.model.district.DistrictMode
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.accentColor
import de.osca.android.district.core.presentation.design.disabledColor
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.core.viewmodel.DistrictPreferenceViewModel
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel

@Composable
fun DistrictPreferenceScreen(
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
    viewModel: DistrictPreferenceViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.requestDistrict()
    }

    var maxDistance by rememberSaveable {
        mutableFloatStateOf(
            (districtViewModel.districtState as? DistrictState.NEARBY)?.maxDistance
                ?: NEARBY_MAX_DISTANCE,
        )
    }

    LoadingWrapper(viewModel.loadingState, retry = viewModel::requestDistrict) { districts ->
        Column(
            modifier =
                Modifier.padding(horizontal = DistrictDesign.Padding.HUGE),
            verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        ) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                val districtModes = remember { DistrictMode.entries }

                districtModes
                    .forEachIndexed { index, districtMode ->
                        SegmentedButton(
                            shape =
                                SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = districtModes.count(),
                                ),
                            onClick = {
                                districtViewModel.updateDistrictMode(districtMode)
                            },
                            selected = districtMode == districtViewModel.districtMode,
                            colors =
                                SegmentedButtonDefaults.colors(
                                    activeContainerColor = Color.accentColor,
                                    activeContentColor = Color.primary,
                                    inactiveContainerColor = Color.disabledColor,
                                    inactiveContentColor = Color.Black,
                                ),
                        ) {
                            Text(
                                stringResource(id = districtMode.title),
                                fontSize = DistrictDesign.Size.Font.NORMAL_TEXT,
                            )
                        }
                    }
            }

            when (districtViewModel.districtMode) {
                DistrictMode.ALL -> Box {}
                DistrictMode.DISTRICT ->
                    DistrictDropDown(
                        districtViewModel = districtViewModel,
                        districts = districts,
                    )

                DistrictMode.NEARBY -> {
                    MaxDistanceSlider(
                        value = maxDistance,
                        maxValue = NEARBY_MAX_DISTANCE,
                        onValueChange = { value ->
                            maxDistance = value
                            districtViewModel.debouncedUpdateMaxdistance(maxDistance)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MaxDistanceSlider(
    modifier: Modifier = Modifier,
    value: Float = NEARBY_MAX_DISTANCE,
    minValue: Float = NEARBY_MIN_DISTANCE,
    maxValue: Float = NEARBY_MAX_DISTANCE,
    onValueChange: (Float) -> Unit,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.DEFAULT),
    ) {
        Slider(
            value = value,
            onValueChange,
            valueRange = minValue..maxValue,
            modifier = Modifier.weight(1f),
            colors =
                SliderDefaults.colors(
                    activeTrackColor = Color.primary,
                    inactiveTrackColor = Color.disabledColor,
                    thumbColor = Color.primary,
                    disabledThumbColor = Color.primary,
                ),
        )
        Text(
            "${if (value == NEARBY_MAX_DISTANCE) '∞' else value.toInt()} km",
            modifier = Modifier.defaultMinSize(minWidth = DistrictDesign.Size.Icon.HUGE),
            color = Color.primary,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictDropDown(
    modifier: Modifier = Modifier,
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
    districts: List<District>,
) {
    var expanded by remember { mutableStateOf(false) }
    val expandedEnabled = districtViewModel.districtMode == DistrictMode.DISTRICT

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {
            if (expandedEnabled) {
                expanded = !expanded
            }
        },
    ) {
        TextField(
            enabled = expandedEnabled,
            value = districtViewModel.selectedDistrict?.name ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = Color.disabledColor,
                    unfocusedContainerColor = Color.disabledColor,
                    focusedIndicatorColor = Color.primary,
                    unfocusedIndicatorColor = Color.primary,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.disabledColor,
        ) {
            districts.forEach { district ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = district.name,
                            fontSize = DistrictDesign.Size.Font.DEFAULT,
                        )
                    },
                    onClick = {
                        districtViewModel.updateSelectedDistrict(district)
                        expanded = false
                    },
                )
            }
        }
    }
}
