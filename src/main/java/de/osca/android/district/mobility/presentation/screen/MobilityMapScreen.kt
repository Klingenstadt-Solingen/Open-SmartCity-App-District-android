package de.osca.android.district.mobility.presentation.screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.MapUiSettings
import de.osca.android.district.core.presentation.component.DistrictTopBar
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.mobility.data.model.Mobility
import de.osca.android.district.mobility.presentation.components.Map
import de.osca.android.district.mobility.presentation.components.MobilitySearchSheet
import de.osca.android.district.mobility.viewmodel.MobilityMapViewModel

enum class SheetValue { Collapsed, PartiallyExpanded, Expanded }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobilityMapScreen(mobilityMapViewModel: MobilityMapViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        mobilityMapViewModel.requestMobilityTypes()
        mobilityMapViewModel.requestMobilityObjects()
    }

    val selectedMobility = remember { mutableStateOf<Mobility?>(null) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        topBar = {
            DistrictTopBar()
        },
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.White,
        sheetPeekHeight = BottomSheetDefaults.SheetPeekHeight + 38.dp + DistrictDesign.Padding.BIGGER,
        sheetContent = {
            MobilitySearchSheet(selectedMobility = selectedMobility.value)
        },
    ) {
        LoadingWrapper(loadingState = mobilityMapViewModel.loadingState) { mobilityObjects ->
            Map(
                onMarkerClick = { objectId -> },
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                mobilities = mobilityObjects,
                mapSettings =
                    MapUiSettings(
                        compassEnabled = true,
                        rotationGesturesEnabled = true,
                        scrollGesturesEnabled = true,
                        tiltGesturesEnabled = true,
                        mapToolbarEnabled = true,
                        indoorLevelPickerEnabled = false,
                        myLocationButtonEnabled = true,
                        zoomControlsEnabled = true,
                        zoomGesturesEnabled = true,
                    ),
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.NEXUS_7,
)
@Composable
fun EventDetailMapContentPreview() {
}
