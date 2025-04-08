package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun LoadingImage(
    url: String,
    modifier: Modifier = Modifier,
    default: @Composable () -> (Unit) = {},
    contentScale: ContentScale = ContentScale.Fit,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        "",
        modifier = modifier,
        error = {
            default()
        },
        loading = {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                ProgressIndicator()
            }
        },
        contentScale = contentScale,
    )
}
