package de.osca.android.district.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle

@Composable
fun ErrorMessage(
    errorMessage: String,
    retry: (() -> Unit)? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(DistrictDesign.Padding.SMALL),
                style = defaultTextStyle(),
            )
            retry?.let {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    elevation = ButtonDefaults.buttonElevation(DistrictDesign.ELEVATION_MEDIUM, DistrictDesign.ELEVATION_MEDIUM),
                    shape = DistrictDesign.ROUNDED_SHAPE_SMALL,
                    onClick = it,
                ) {
                    Text(
                        text = stringResource(id = R.string.try_again),
                        style = defaultTextStyle(),
                    )
                }
            }
        }
    }
}
