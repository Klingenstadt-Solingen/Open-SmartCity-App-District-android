package de.osca.android.district.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.data.model.EventOpeningHour
import de.osca.android.district.event.data.model.EventSponsor
import de.osca.android.essentials.utils.extensions.resetWith
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel
    @Inject
    constructor(
        private val repository: EventRepository,
    ) : ViewModel(),
        Loadable<Event> {
        override var loadingState by mutableStateOf<LoadingState<Event>>(LoadingState.Loading)
        var eventBooths = mutableStateListOf<EventBooth>()
        var eventSponsors = mutableStateListOf<EventSponsor>()
        var eventOpeningHours = mutableStateListOf<EventOpeningHour>()

        fun requestEventData(objectId: String) {
            loadingStateScope {
                val event = repository.getEventById(objectId)
                eventBooths.resetWith(repository.getEventBoothsByEventId(objectId))
                eventSponsors.resetWith(repository.getEventSponsorByEventId(objectId))
                eventOpeningHours.resetWith(repository.getEventOpeningHoursByEventId(objectId))
                finishLoading(event)
            }
        }
    }
