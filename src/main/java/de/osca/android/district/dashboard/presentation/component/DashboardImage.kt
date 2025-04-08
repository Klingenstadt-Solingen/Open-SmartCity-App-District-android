package de.osca.android.district.dashboard.presentation.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import de.osca.android.district.R
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel

@Composable
fun DashboardImage(districtViewModel: DistrictViewModel = sharedHiltViewModel()) {
    Box {
        Crossfade(
            targetState = districtViewModel.districtState,
            label = "",
        ) { districtState ->
            if (districtState is DistrictState.DISTRICT) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = districtState.district.image.url,
                    contentDescription = "Header Image",
                    contentScale = ContentScale.FillWidth
                )
            } else {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.dashboard_image),
                    contentDescription = "Header Image",
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
        Spacer(
            modifier =
                Modifier
                    .fillMaxSize()
                    .alpha(0.35f)
                    .background(Color.Black),
        )
    }
}

@Preview
@Composable
fun DashboardImagePreview() {
    DashboardImage()
}
