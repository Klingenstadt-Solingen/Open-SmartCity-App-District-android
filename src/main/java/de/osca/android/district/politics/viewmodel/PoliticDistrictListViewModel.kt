package de.osca.android.district.politics.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.boundaries.DistrictRepository
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.viewmodel.Loadable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class PoliticDistrictListViewModel
    @Inject
    constructor(
        private val districtRepository: DistrictRepository,
    ) :  ViewModel(), Loadable<List<District>> {

    override var loadingState by mutableStateOf<LoadingState<List<District>>>(LoadingState.Loading)

    init {
        refreshDistricts()
    }

    fun refreshDistricts() {
        viewModelScope.launch {
            finishLoading(districtRepository.getDistricts())
        }
    }
}
