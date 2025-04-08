package de.osca.android.district.pointofinterest.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.data.resetWith
import de.osca.android.district.pointofinterest.data.boundary.PoiRepository
import de.osca.android.essentials.domain.entity.ParsePOI
import javax.inject.Inject

@HiltViewModel
class PoiMapViewModel
    @Inject
    constructor(
        private val repository: PoiRepository,
    ) : ViewModel() {
        var pointOfInterests = mutableStateListOf<ParsePOI>()

        suspend fun requestPois(
            categoryIds: Set<String>,
            districtState: DistrictState,
        ) {
            pointOfInterests.resetWith(repository.getPois(categoryIds, districtState))
        }
    }
