package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.ProgressIndicator
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun PressReleaseListItemImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Box(modifier = modifier.clipToBounds()) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .align(Alignment.Center),
            clipToBounds = false,
            loading = {
                Box(modifier = Modifier.fillMaxSize()) {
                    ProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            error = {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(DistrictDesign.Padding.MEDIUM),
                    painter = painterResource(id = R.drawable.ic_megaphone_with_soundwave),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PressReleaseListItemImagePreview() {
    PressReleaseListItemImage(
        "https://images.pexels.com/photos/1366919/pexels-photo-1366919.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
    )
}
