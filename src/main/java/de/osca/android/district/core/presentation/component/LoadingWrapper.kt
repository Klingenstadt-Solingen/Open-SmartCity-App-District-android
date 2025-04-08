package de.osca.android.district.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.LoadingState.Error
import de.osca.android.district.core.data.model.LoadingState.Loaded
import de.osca.android.district.core.data.model.LoadingState.Loading
import de.osca.android.district.core.presentation.ErrorMessage

@Composable
fun <T> LoadingWrapper(
    loadingState: LoadingState<T>,
    retry: (() -> Unit)? = null,
    // TODO: Remove replacement and/or get it aligned with IOS
    replacement: (@Composable () -> Unit) = { FullscreenProgressIndicator() },
    content: @Composable (data: T) -> Unit,
) {
    when (loadingState) {
        is Loading -> {
            replacement.invoke()
        }

        is Error ->
            ErrorMessage(
                errorMessage = stringResource(id = loadingState.errorMessage),
                retry = retry,
            )

        is Loaded<T> -> {
            content(loadingState.data)
        }
    }
}

@Composable
private fun FullscreenProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        ProgressIndicator()
    }
}
