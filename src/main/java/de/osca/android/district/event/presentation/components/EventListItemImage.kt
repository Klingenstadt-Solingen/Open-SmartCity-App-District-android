package de.osca.android.district.event.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.ProgressIndicator

@Composable
fun EventListItemImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        model = url?.ifBlank { null },
        contentDescription = contentDescription,
        loading = {
            Box(modifier = Modifier.fillMaxSize()) {
                ProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.ic_confetti),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        },
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}