package de.osca.android.district.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.boundaries.DistrictRepository
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.district.District
import javax.inject.Inject

@HiltViewModel
class DistrictPreferenceViewModel
    @Inject
    constructor(
        private val districtRepository: DistrictRepository,
    ) : ViewModel(), Loadable<List<District>> {
        override var loadingState by mutableStateOf<LoadingState<List<District>>>(LoadingState.Loading)

        fun requestDistrict() {
            loadingStateScope {
                finishLoading(districtRepository.getDistricts())
            }
        }
    }
