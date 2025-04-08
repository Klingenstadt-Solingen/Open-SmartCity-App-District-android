package de.osca.android.district.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.weather.viewmodel.WeatherDetailViewModel

@Composable
fun TemperatureCard(
    text: String,
    value: String,
    unit: String,
    image: String? = null,
) {
    val temperatureCardContentDescription =
        stringResource(id = R.string.station_temperature, value, unit)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(size = 10.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .clearAndSetSemantics {
                    contentDescription = temperatureCardContentDescription
                },
    ) {
        Box(
            modifier =
                Modifier.fillMaxWidth().height(140.dp),
        ) {
            SubcomposeAsyncImage(
                model = image,
                contentDescription = null,
                loading = {
                    Image(
                        painter = painterResource(id = R.drawable.dashboard_image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Multiply),
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.dashboard_image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Multiply),
                    )
                },
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Multiply),
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
            ) {
                val id = WeatherDetailViewModel.getWeatherRessourceIcon(text)

                Icon(
                    painter = painterResource(id),
                    contentDescription = "Description Icon",
                    Modifier.size(50.dp),
                    tint = Color.White,
                )

                Text(
                    text = "$value ",
                    style =
                        defaultTextStyle(
                            fontSize = 50.sp,
                            Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                )
                Text(
                    text = unit,
                    style =
                        defaultTextStyle(
                            fontSize = 50.sp,
                            Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                )
            }
        }
    }
}
