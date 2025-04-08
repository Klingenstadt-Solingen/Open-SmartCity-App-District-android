package de.osca.android.district.mobility.presentation.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.mobility.data.model.MobilityType
import de.osca.android.district.mobility.viewmodel.MobilityMapViewModel

@Composable
fun MobilityStationDetailScreen(
    id: String,
    viewModel: MobilityMapViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.requestMobilityObjectById(MobilityType.SCOOTER,"")
    }


    TopBarScaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Station")
        }

    }
}




@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.NEXUS_7,
)
@Composable
fun MobilityStationDetailScreenPreview() {
    MobilityStationDetailScreen("")

}
