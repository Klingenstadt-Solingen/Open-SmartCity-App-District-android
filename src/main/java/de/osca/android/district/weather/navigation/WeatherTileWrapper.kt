package de.osca.android.district.weather.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.navigation.NavItem
import de.osca.android.district.core.presentation.component.DashboardDefaultTile
import de.osca.android.district.core.presentation.component.DashboardTileWrapper
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.weather.viewmodel.WeatherViewModel

object WeatherTileWrapper : DashboardTileWrapper {
    override val position = 1
    override var routeItem: MutableState<NavItem> =
        mutableStateOf(WeatherNavItems.WeatherScreenNavItem)
    override var tileContentDescription: MutableState<String> = mutableStateOf("")

    @Composable
    override fun ContentView() {
        val viewModel: WeatherViewModel = hiltViewModel()
        tileContentDescription.value = ""
        viewModel.measurement?.let {
            tileContentDescription.value +=
                stringResource(
                    id = R.string.weather_temperature_is,
                    it.value,
                    it.unit,
                )
        }
        tileContentDescription.value += "\n" + stringResource(id = R.string.weather_module_title)

        DashboardDefaultTile(
            title = R.string.weather_module_title,
            image = R.drawable.ic_cloud,
            content = {
                viewModel.measurement?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = String.format("%.0f", it.value),
                            style =
                                defaultTextStyle(
                                    30,
                                    Color.primary,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                        // Cant align them to button properly TODO: fix alignment
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = it.unit,
                            style =
                                defaultTextStyle(
                                    fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                                    Color.primary,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    }
                }
            },
        )
    }
}
