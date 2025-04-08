package de.osca.android.district.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.primary
import de.osca.android.district.weather.data.model.WeatherMeasurement
import de.osca.android.district.weather.viewmodel.WeatherDetailViewModel

@Composable
fun WeatherMeasurementCard(measurement: WeatherMeasurement) {
    val name = measurement.name
    val value = measurement.value.toString()
    val unit = measurement.unit
    val cardContentDescription = stringResource(id = R.string.measurement_is, name, value, unit)

    Card(
        shape = DistrictDesign.ROUNDED_SHAPE,
        colors =
            CardDefaults.cardColors(
                Color.primary,
                Color.White,
                Color.primary,
                Color.primary,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = DistrictDesign.ELEVATION_MEDIUM,
                pressedElevation = DistrictDesign.ELEVATION_MEDIUM,
            ),
        modifier =
            Modifier
                .fillMaxWidth().padding(top = DistrictDesign.Padding.DEFAULT)
                .clearAndSetSemantics {
                    contentDescription = cardContentDescription
                },
    ) {
        Row(
            modifier = Modifier.padding(DistrictDesign.Padding.MEDIUM),
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.MEDIUM),
        ) {
            Box(
                modifier =
                    Modifier
                        .align(Alignment.CenterVertically)
                        .size(DistrictDesign.Size.Icon.BIGGER),
            ) {
                if (WeatherDetailViewModel.hasWeatherRessourceIcon(name)) {
                    val id = WeatherDetailViewModel.getWeatherRessourceIcon(name)

                    Icon(
                        painter = painterResource(id),
                        contentDescription = "Description Icon",
                        Modifier.size(DistrictDesign.Size.Icon.BIGGER),
                    )
                }
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.CenterVertically),
            ) {
                Text(name, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "$value ",
                        fontSize = DistrictDesign.Size.Font.HEADLINE,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(unit, fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE)
                }
            }
        }
    }
}
