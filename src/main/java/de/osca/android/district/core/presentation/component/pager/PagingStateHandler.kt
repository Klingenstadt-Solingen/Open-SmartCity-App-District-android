package de.osca.android.district.core.presentation.component.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.data.repository.ApiError
import de.osca.android.district.core.presentation.ErrorMessage
import de.osca.android.district.core.presentation.component.ProgressIndicator
import de.osca.android.district.core.presentation.design.DistrictDesign

fun <T : Any> LazyGridScope.applyPagingHandler(pagingItems: LazyPagingItems<T>) {
    pagingItems.apply {
        when (val refreshState = loadState.refresh) {
            is LoadState.Error -> {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    var errorMessageResource = R.string.error_message_unknown
                    val error = refreshState.error
                    if (error is ApiError) {
                        errorMessageResource = error.messageResource()
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = DistrictDesign.Padding.BIG),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ErrorMessage(
                            errorMessage = stringResource(id = errorMessageResource),
                            retry = {
                                refresh()
                            },
                        )
                    }
                }
            }

            is LoadState.Loading -> {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = DistrictDesign.Padding.BIG),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProgressIndicator()
                    }
                }
            }

            else -> {
                if (itemCount == 0) {
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = DistrictDesign.Padding.BIG),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            ErrorMessage(
                                errorMessage = stringResource(id = R.string.error_message_no_result),
                            )
                        }
                    }
                }
            }
        }
        when (loadState.append) {
            is LoadState.Loading -> {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(DistrictDesign.Size.Icon.BIGGER),
                        contentAlignment = Alignment.Center,
                    ) {
                        ProgressIndicator()
                    }
                }
            }

            else -> {}
        }
    }
}

fun <T : Any> LazyListScope.applyPagingHandler(pagingItems: LazyPagingItems<T>) {
    pagingItems.apply { ->
        when (val refreshState = loadState.refresh) {
            is LoadState.Error -> {
                item {
                    var errorMessageResource = R.string.error_message_unknown
                    val error = refreshState.error
                    if (error is ApiError) {
                        errorMessageResource = error.messageResource()
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = DistrictDesign.Padding.BIG),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ErrorMessage(
                            errorMessage = stringResource(id = errorMessageResource),
                            retry = {
                                refresh()
                            },
                        )
                    }
                }
            }

            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = DistrictDesign.Padding.BIG),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProgressIndicator()
                    }
                }
            }

            else -> {
                if (itemCount == 0) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = DistrictDesign.Padding.BIG),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            ErrorMessage(
                                errorMessage = stringResource(id = R.string.error_message_no_result),
                            )
                        }
                    }
                }
            }
        }
        when (loadState.append) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(DistrictDesign.Size.Icon.BIGGER),
                        contentAlignment = Alignment.Center,
                    ) {
                        ProgressIndicator()
                    }
                }
            }

            else -> {}
        }
    }
}
