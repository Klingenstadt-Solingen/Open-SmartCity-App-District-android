package de.osca.android.district.mobility.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.mobility.data.model.Mobility
import de.osca.android.district.mobility.data.model.MobilityType
import de.osca.android.district.mobility.data.model.MobilityTypeDto
import de.osca.android.district.mobility.data.repository.MobilityRepository
import de.osca.android.essentials.utils.extensions.resetWith
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobilityMapViewModel
@Inject
constructor(
    private val repository: MobilityRepository,
) : ViewModel(), Loadable<List<Mobility>> {
    override var loadingState by mutableStateOf<LoadingState<List<Mobility>>>(LoadingState.Loading)
    var selectedMobilityObjects = mutableStateOf<Mobility?>(null)
    var mobilityTypes = mutableStateListOf<MobilityTypeDto>()
    var selectedMobilityType = mutableStateOf(MobilityType.SCOOTER)


    fun requestMobilityObjects() {
        viewModelScope.launch {
            finishLoading(repository.getMobilityObjects(type = selectedMobilityType.value, limit = 10000000, lat = 51.171717999, lon = 7.085664999, rangeInMeter = 10000.0))
        }
    }

    fun requestMobilityTypes() {
        viewModelScope.launch {
            mobilityTypes.resetWith(repository.getMobilityTypeDto())
        }
    }

    fun requestMobilityObjectById(type: MobilityType, id:String) {
        viewModelScope.launch {
            selectedMobilityObjects.value = repository.getMobilityObjectById(type,id)
        }
    }
}
