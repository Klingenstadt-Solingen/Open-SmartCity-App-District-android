package de.osca.android.district.pointofinterest.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.pointofinterest.data.boundary.PoiRepository
import de.osca.android.essentials.domain.entity.ParsePOI
import javax.inject.Inject

@HiltViewModel
class PoiDetailViewModel
    @Inject
    constructor(private val repository: PoiRepository) : ViewModel(), Loadable<ParsePOI> {
        override var loadingState by mutableStateOf<LoadingState<ParsePOI>>(LoadingState.Loading)

        fun requestPoiById(objectId: String) {
            loadingStateScope {
                finishLoading(repository.getPoiById(objectId))
            }
        }
    }
