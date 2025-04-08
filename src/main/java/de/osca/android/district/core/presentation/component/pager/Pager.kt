package de.osca.android.district.core.presentation.component.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign
import kotlinx.coroutines.launch

@Composable
fun <D> Pager(
    modifier: Modifier = Modifier,
    list: List<D>,
    pagerState: PagerState = rememberPagerState(pageCount = { list.count() }),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (D) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val semanticPagerButton = stringResource(id = R.string.pager_button)

    Column(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
            contentPadding = contentPadding,
        ) { page ->
            content(list[page])
        }

        if (pagerState.pageCount > 1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Padding.SMALLEST),
                verticalAlignment = Alignment.Bottom,
            ) {
                for (index in 0..<pagerState.pageCount) {
                    val isSelected = index == pagerState.currentPage

                    PagerButton(
                        modifier =
                            Modifier.weight(1f).semantics {
                                contentDescription = "$semanticPagerButton ${index + 1}"
                                role =
                                    Role.Button // else it autoselects the pager button when it autoscrolls
                                if (isSelected) {
                                    selected = true
                                }
                            },
                        selected = isSelected,
                        action = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
        }
    }
}
