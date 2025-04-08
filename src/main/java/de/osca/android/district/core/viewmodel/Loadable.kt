package de.osca.android.district.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.osca.android.district.R
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.repository.ApiError
import kotlinx.coroutines.launch

interface Loadable<T> {
    var loadingState: LoadingState<T>

    private fun updateLoadingState(loadingState: LoadingState<T>) {
        if (this.loadingState != loadingState) {
            this.loadingState = loadingState
        }
    }

    fun finishLoading(data: T) {
        updateLoadingState(LoadingState.Loaded(data))
    }

    fun handleError(
        error: Throwable,
        noResult: Int = R.string.error_message_no_result,
    ) {
        if (error is ApiError) {
            updateLoadingState(
                LoadingState.Error(errorMessage = error.messageResource()),
            )
        }
    }

    fun <T> T.loadingStateScope(block: suspend () -> Unit) where T : ViewModel {
        viewModelScope.launch {
            updateLoadingState(LoadingState.Loading)
            try {
                block()
            } catch (error: Throwable) {
                handleError(error)
            }
        }
    }
}
