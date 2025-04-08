package de.osca.android.district.pressrelease.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.LoadingWrapper
import de.osca.android.district.core.presentation.component.pager.Pager
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.viewmodel.DistrictViewModel
import de.osca.android.district.core.viewmodel.sharedHiltViewModel
import de.osca.android.district.pressrelease.viewmodel.PressReleaseWidgetViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

private const val PAGER_TICK = 8
private var counter = 0

@Composable
fun PressReleaseWidget(
    modifier: Modifier = Modifier,
    viewModel: PressReleaseWidgetViewModel = hiltViewModel(),
    districtViewModel: DistrictViewModel = sharedHiltViewModel(),
) {
    LaunchedEffect(districtViewModel.districtState) {
        viewModel.requestPressReleases(districtViewModel.districtState)
    }

    val scope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        LoadingWrapper(viewModel.loadingState) { pressReleases ->
            val pagerState =
                rememberPagerState(pageCount = {
                    pressReleases.count()
                })
            if (pagerState.pageCount > 0) {
                LaunchedEffect(pagerState.currentPage) {
                    // animateScrollToPage stucks if the pager is hold by a finger while the animation is triggered
                    // this while loop ensures that we restart the animation
                    while (true) {
                        counter = 0
                        while (counter <= PAGER_TICK) {
                            delay(1.seconds)
                            counter++
                        }

                        with(pagerState) {
                            val selectedTab = (currentPage + 1) % pageCount

                            job?.cancel()

                            job =
                                // we have to start the animation in another scope so that is does not get canceled mid animation
                                // but as we cannot reuse a canceled scope, we have to wrap it into a job that can be cancelled.
                                scope.launch {
                                    animateScrollToPage(
                                        page = selectedTab,
                                    )
                                }
                        }
                    }
                }
            }

            if (pressReleases.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.no_press_release_found),
                        fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE
                    )
                }
            } else {
                Pager(
                    modifier = Modifier.clip(DistrictDesign.ROUNDED_SHAPE),
                    list = pressReleases,
                    pagerState = pagerState,
                ) {
                    PressReleaseWidgetItem(pressRelease = it)
                }
            }
        }
    }
}
