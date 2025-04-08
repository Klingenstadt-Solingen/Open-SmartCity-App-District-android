package de.osca.android.district.core.data.model

import androidx.annotation.StringRes

sealed interface LoadingState<out T> {
    data object Loading : LoadingState<Nothing>

    data class Loaded<out T>(val data: T) : LoadingState<T>

    data class Error(
        @StringRes val errorMessage: Int,
    ) : LoadingState<Nothing>
}
