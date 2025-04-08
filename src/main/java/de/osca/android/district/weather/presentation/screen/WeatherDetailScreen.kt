package de.osca.android.district.weather.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.button.SelectableButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.weather.data.model.Weather
import de.osca.android.district.weather.data.model.WeatherMeasurement
import de.osca.android.district.weather.presentation.components.WeatherScrollContent

@Composable
fun WeatherDetailScreen(
    list: List<WeatherMeasurement> = emptyList(),
    weather: Weather,
    deselectWeather: () -> (Unit),
) {
    val semanticsWeatherStationSelection = stringResource(id = R.string.weather_station_selection)

    TopBarScaffold { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(
                        horizontal = DistrictDesign.Padding.BIG,
                    ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(bottom = DistrictDesign.Padding.SMALL),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.HUGE),
                ) {
                    Text(
                        stringResource(id = R.string.weather_module_title),
                        fontSize = DistrictDesign.Size.Font.HEADLINE,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        weather.shortName ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = DistrictDesign.Size.Font.SUB_TITLE,
                        modifier = Modifier.padding(bottom = DistrictDesign.Padding.SMALL),
                    )
                }

                SelectableButton(
                    modifier =
                        Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .align(Alignment.Bottom)
                            .clearAndSetSemantics {
                                role = Role.Button
                                contentDescription = semanticsWeatherStationSelection
                            },
                    enabled = true,
                    onClick = {
                        deselectWeather()
                    },
                    content = {
                        Icon(
                            modifier =
                                Modifier
                                    .size(DistrictDesign.Size.Icon.BIG),
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                        )
                    },
                )
            }
            WeatherScrollContent(list, image = weather.image)
        }
    }
}
