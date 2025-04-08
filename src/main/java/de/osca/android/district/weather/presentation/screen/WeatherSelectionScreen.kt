package de.osca.android.district.weather.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.GeneralButton
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.weather.data.model.Weather
import de.osca.android.district.weather.presentation.components.WeatherListItem
import de.osca.android.district.weather.viewmodel.WeatherDetailViewModel
import de.osca.android.district.weather.viewmodel.WeatherSelectionViewModel

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WeatherSelectionScreen(
    scrollState: LazyListState,
    viewModel: WeatherSelectionViewModel = hiltViewModel(),
    weatherDetailViewModel: WeatherDetailViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.updateDistrict(districtViewModel.districtState)
    }

    val weathers = viewModel.weathers.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(weathers, isRefreshing)

    TopBarScaffold { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding).padding(horizontal = DistrictDesign.Padding.BIG),
        ) {
            Text(
                stringResource(id = R.string.weather_module_title),
                fontSize = DistrictDesign.Size.Font.HEADLINE,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
            )
            val state = rememberPullToRefreshState()
            PullToRefreshBox(
                state = state,
                isRefreshing = isRefreshing.value,
                onRefresh = weathers::refresh,
                modifier =
                    Modifier.fillMaxWidth(),
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        modifier =
                            Modifier
                                .align(Alignment.TopCenter)
                                .clearAndSetSemantics { disabled() },
                        isRefreshing = isRefreshing.value,
                        state = state,
                    )
                },
            ) {
                LazyColumn(
                    state = scrollState,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.SMALL),
                    contentPadding = PaddingValues(vertical = DistrictDesign.Padding.MEDIUM),
                ) {
                    val fallback = 9999.0

                    items(weathers.itemCount) { index ->
                        weathers[index]?.let { item ->
                            val isSelected = item.objectId == weatherDetailViewModel.getSelectedId()
                            val distance = getDistance(viewModel.geopoint, item.geopoint, fallback)

                            GeneralButton(
                                onClick = {
                                    weatherDetailViewModel.changeSelectedWeather(item)
                                },
                                shape = DistrictDesign.ROUNDED_SHAPE,
                                elevation =
                                    CardDefaults.elevatedCardElevation(
                                        defaultElevation = DistrictDesign.ELEVATION_SMALL,
                                        pressedElevation = DistrictDesign.ELEVATION_SMALL,
                                    ),
                                modifier =
                                    Modifier
                                        .defaultMinSize(20.dp, 15.dp)
                                        .semantics {
                                            this.role = Role.Button
                                            if (isSelected) {
                                                this.selected = true
                                            }
                                        },
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                WeatherListItem(
                                    name = item.name,
                                    distance = distance,
                                    selected = isSelected,
                                )
                            }
                        }
                    }
                    applyPagingHandler(weathers)
                }
            }
        }
    }
}

fun getDistance(
    from: ParseGeoPoint?,
    to: ParseGeoPoint,
    fallbackValue: Double,
): Double {
    val distance = from?.distanceInKilometersTo(to) ?: fallbackValue

    if (distance > fallbackValue) return fallbackValue

    return distance
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.NEXUS_7,
)
@Composable
fun WeatherSelectionContentPreview() {
    ParseObject.registerSubclass(Weather::class.java)
    var list: List<Weather> = emptyList()
    val mes = Weather()
    mes.name = "Piepersberg 17"
    list = list.plus(mes)
    val mes1 = Weather()
    mes1.name = "Eipaßstraße 54"
    list = list.plus(mes1)
    val mes2 = Weather()
    mes2.name = "Abteiweg"
    list = list.plus(mes2)
    val mes3 = Weather()
    mes3.name = "Mittelitter"
    list = list.plus(mes3)
    val mes4 = Weather()
    mes4.name = "Weyer Str. / Liebigstr."
    list = list.plus(mes4)
    val mes5 = Weather()
    mes5.name = "Lützowstr. / Untere Holzstr."
}
