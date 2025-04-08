package de.osca.android.district.event.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.EventSponsor
import de.osca.android.district.event.data.model.EventTag
import de.osca.android.essentials.utils.extensions.resetWith
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventBoothDetailViewModel
@Inject
constructor(
    private val repository: EventRepository,
) : ViewModel(){

    var eventTags = mutableStateListOf<EventTag>()
    var eventSponsors = mutableStateListOf<EventSponsor>()

    fun requestTags(objectId: String) {
        viewModelScope.launch {
            eventTags.resetWith(repository.getEventTagsByEventBoothId(objectId))
        }
    }

    fun requestSponsors(objectId: String) {
        viewModelScope.launch {
            eventSponsors.resetWith(repository.getEventBoothSponsorByEventBoothId(objectId))
        }
    }
}
