package de.osca.android.district.core.paging

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.paging.LoadState
import androidx.paging.PagingDataPresenter
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PagingRefreshEffect(
    items: LazyPagingItems<*>,
    refreshState: MutableState<Boolean>,
) {
    LaunchedEffect(key1 = items) {
        try {
            val pagingDataPresenter =
                items.javaClass.getDeclaredField("pagingDataPresenter")

            pagingDataPresenter.isAccessible = true
            val f =
                (pagingDataPresenter.get(items) as PagingDataPresenter<*>)
                    .loadStateFlow
                    // Initial State: old will be null; the first "new.refresh" will be Loading
                    // Refresh State: old.refresh will not be Loading and new.refresh will be loading
                    // See documentation above for more info
                    .distinctUntilChanged { old, new ->
                        refreshState.value =
                            old != null &&
                            old.refresh !is LoadState.Loading &&
                            new?.refresh is LoadState.Loading
                        refreshState.value
                    }.collect()
        } catch (e: Exception) {
            // ignore exception as never change the refresh state
        }
    }
}

// TODO(Alex): Implement fake loading; sometimes load times are so short, that the loading spinner freezes in one state
