package de.osca.android.district.pressrelease.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.pressrelease.data.boundaries.PressReleaseRepository
import de.osca.android.district.pressrelease.data.model.PressRelease
import javax.inject.Inject

@HiltViewModel
class PressReleaseDetailViewModel
    @Inject
    constructor(private val repository: PressReleaseRepository) : ViewModel(), Loadable<PressRelease> {
        override var loadingState by mutableStateOf<LoadingState<PressRelease>>(LoadingState.Loading)

        fun fetchPressRelease(objectId: String) {
            loadingStateScope {
                finishLoading(repository.getPressReleaseById(objectId))
            }
        }
    }
