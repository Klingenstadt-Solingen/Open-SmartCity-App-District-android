package de.osca.android.district.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.Event
import javax.inject.Inject

@HiltViewModel
class EventWidgetViewModel
    @Inject
    constructor(
        private val repository: EventRepository,
    ) : ViewModel(), Loadable<List<Event>> {
        override var loadingState by mutableStateOf<LoadingState<List<Event>>>(LoadingState.Loading)
        suspend fun requestNextEvent(districtState: DistrictState) {
            loadingStateScope {
                val event = repository.getNextEvents(districtState)
                finishLoading(event)
            }
        }
    }
