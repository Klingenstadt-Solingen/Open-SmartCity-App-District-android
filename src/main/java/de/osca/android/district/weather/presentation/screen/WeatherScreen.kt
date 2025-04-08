package de.osca.android.district.weather.presentation.screen

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.weather.viewmodel.WeatherDetailViewModel

/**
 * Main Screen. The [WeatherSelectionScreen] is just for selection and not meant to be a navigation target.
 * It would have made more sense, if the selection screen would be a bottom sheet
 */
@Composable
fun WeatherScreen(viewModel: WeatherDetailViewModel = hiltViewModel()) {
    val scrollState = rememberLazyListState()

    LoadingWrapper(loadingState = viewModel.loadingState) { weather ->
        if (viewModel.selectedWeatherId == null || weather == null) {
            WeatherSelectionScreen(scrollState)
        } else {
            WeatherDetailScreen(
                weather.getValues(),
                weather,
            ) { viewModel.selectedWeatherId = null }
        }
    }
}
