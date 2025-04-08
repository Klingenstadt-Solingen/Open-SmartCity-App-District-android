package de.osca.android.district.event.presentation.screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.MapUiSettings
import com.parse.ParseObject
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.presentation.components.Map
import de.osca.android.district.event.presentation.screen.sheet.EventBoothDetailSheet
import de.osca.android.district.event.viewmodel.EventDetailViewModel
import de.osca.android.essentials.domain.entity.Coordinates
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailMapScreen(
    objectId: String,
    viewModel: EventDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(objectId) {
        viewModel.requestEventData(objectId)
    }

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val selectedEventBooth = remember { mutableStateOf<EventBooth?>(null) }

    TopBarScaffold { paddingValues ->
        LoadingWrapper(viewModel.loadingState) { event ->
            Map(
                onMarkerClick = { objectId ->
                    selectedEventBooth.value =
                        viewModel.eventBooths.first { eventBooth ->
                            eventBooth.objectId == objectId
                        }
                    showBottomSheet.value = true
                },
                modifier = Modifier.padding(paddingValues).fillMaxHeight().fillMaxWidth(),
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
                eventLocation =
                    Coordinates(
                        latitude = event.geopoint.latitude,
                        longitude = event.geopoint.longitude,
                    ),
                eventBooths = viewModel.eventBooths,
            )
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet.value = false
                    },
                    sheetState = sheetState,
                    containerColor = Color.White,
                ) {
                    selectedEventBooth.value?.let { eventBooth ->
                        EventBoothDetailSheet(eventBooth)
                    }
                }
            }
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
    ParseObject.registerSubclass(Event::class.java)
    val nowDate = Date()
    val event = Event()
    event.name =
        "adfadsf asdfasdfasdfasdfa asdfasdfasdfadsf dfsgdfggdfs  rfdgsfdgs fdsgfdg dsafdsfas"
    event.category = "Ausstellung"
    event.endDate = nowDate
    event.startDate = nowDate
    event.location = HashMap<String, Any>()
    event.url = "https://picsum.photos/200/300"
    event.description = """<br>Lotsenpunkt Solingen<br>
<br>
<br>
<br>
Am 1.09.2023 startete der Lotsenpunkt Solingen – eine Kooperation der Kirchengemeinde St. Sebastian und der Caritas Wuppertal/Solingen e.V. – mit seiner Arbeit.<br>
In den von Kirchengemeinden und Caritasverbänden getragenen Lotsenpunkten kümmern sich die Engagierten um die Unterstützung von Ratsuchenden und leisten vor allem Formularhilfe. Die Zahl der Menschen, die die Lotsenpunkte aufsuchen, nimmt seit einiger Zeit signifikant zu, der Beratungsbedarf steigt kontinuierlich.<br>
8 Engagierte konnten für das neue Projekt gewonnen werden und bringen sich hier zu unterschiedlichen Öffnungszeiten ein. Der Lotsenpunkt Solingen ist in der Servicestelle Engagement auf der Hackhauser Str. 2a, 42697 Solingen beheimatet. Telefon mit AB: 0212 23140050 oder per E-Mail an: lotsenpunkt@st.sebastian<br> <br>Die Öffnungszeiten:<br>
Jeden Montag von 10.00 – 12.00 Uhr<br>
Jeden Mittwoch von 17.00 – 19.00 Uhr<br>
<br>
<br>
Jeden letzten Samstag im Monat beim Wohlfühlmorgen, Hackhauser Str. 16 von 9.00 – 11.00 Uhr<br>
Ergänzt werden die Sprechstunden mittwochs von 14.00 – 17.00 Uhr durch das Beratungsteam Solingen, Konrad-Adenauer-Str. 3, 42651 Solingen.<br>
<br>
Die neu gewonnenen Lotsinnen und Lotsen werden auf den aktuellen Stand der Sozialgesetzgebung, des Aufenthaltsrechts u.ä. sein und auch in anderen Kompetenzen regelmäßig geschult.<br>
Der Beratungsbedarf steigt immer weiter an – Macht mit, werdet Teil eines tollen Teams!<br>
Der Lotsenpunkt Solingen freut sich über weitere Engagierte, die Lust haben, sich dort einzubringen. Das Miteinander im Team ist geprägt von positiver Atmosphäre, regelmäßigen Teamtreffen und Weiterbildungsangeboten. Der Umfang der Einsatzzeit lässt sich individuell vereinbaren und bleibt flexibel. <br>"""
    val address = HashMap<String, Any>()
    address["name"] = "Bergische VHS - Solingen - Mummstraße"
    address["streetAddress"] = "Mummstr. 10"
    address["addressLocality"] = "Solingen"
    address["postalCode"] = "42561"
    event.location!!["address"] = address
}
