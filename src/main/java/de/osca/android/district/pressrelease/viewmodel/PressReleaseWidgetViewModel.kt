package de.osca.android.district.pressrelease.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.pressrelease.data.boundaries.PressReleaseRepository
import de.osca.android.district.pressrelease.data.model.PressRelease
import javax.inject.Inject

@HiltViewModel
class PressReleaseWidgetViewModel
    @Inject
    constructor(
        val repository: PressReleaseRepository,
    ) : ViewModel(), Loadable<List<PressRelease>> {
        override var loadingState by mutableStateOf<LoadingState<List<PressRelease>>>(LoadingState.Loading)

        fun requestPressReleases(
            districtState: DistrictState = DistrictState.ALL,
            limit: Int = 5,
        ) {
            loadingStateScope {
                val pressReleases =
                    repository.getPressReleases(districtState = districtState, limit = limit, skip = 0)

                finishLoading(pressReleases)
            }
        }
    }
