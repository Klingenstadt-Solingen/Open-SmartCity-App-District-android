package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.osca.android.district.core.SECONDARY_COLOR
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER)) {
    CircularProgressIndicator(
        color = SECONDARY_COLOR,
        strokeWidth = 8.dp,
        modifier = modifier,
    )
}
