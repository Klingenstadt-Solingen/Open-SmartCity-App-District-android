package de.osca.android.district.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.osca.android.district.weather.data.model.WeatherMeasurement

@Composable
fun WeatherScrollContent(
    measurementList: List<WeatherMeasurement>,
    modifier: Modifier = Modifier,
    image: String? = null,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp), // causes double spacing for single item
    ) {
        item {
            val measurement: WeatherMeasurement? =
                measurementList.find { weatherMeasurement ->
                    weatherMeasurement.name == "Temperatur"
                }
            if (measurement != null) {
                TemperatureCard(
                    text = measurement.name,
                    value = String.format("%.1f", measurement.value),
                    unit = measurement.unit,
                    image = image,
                )
            }
        }
        items(items = measurementList) { measurement ->
            if (measurement.name != "Temperatur") {
                WeatherMeasurementCard(measurement = measurement)
            }
        }
    }
}
