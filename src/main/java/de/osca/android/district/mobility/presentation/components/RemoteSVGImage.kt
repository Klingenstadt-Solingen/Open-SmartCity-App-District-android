package de.osca.android.district.mobility.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import de.osca.android.district.core.presentation.design.DistrictDesign

@Composable
fun RemoteSVGImage(url: String,
                   modifier: Modifier = Modifier.size(DistrictDesign.Size.Icon.BIGGER),
                   color: Color? = null,
                   description: String = "",
                   contentScale: ContentScale = ContentScale.Fit) {

    return SubcomposeAsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = description,
        modifier = modifier,
        colorFilter = if (color!=null) ColorFilter.tint(color) else null,
        contentScale = contentScale,
    )
}