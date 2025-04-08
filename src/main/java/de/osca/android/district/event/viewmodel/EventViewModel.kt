package de.osca.android.district.event.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.data.model.DateDataStoreDelegate
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.event.EventDatabase
import de.osca.android.district.event.EventModule
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventEntity
import de.osca.android.district.event.eventDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class EventViewModel
    @Inject
    constructor(
        private val eventRepository: EventRepository,
        eventDatabase: EventDatabase,
        application: Application,
    ) : ViewModel() {
        private var watchedAt =
            DateDataStoreDelegate(
                application.applicationContext.eventDataStore,
            )
        private var eventDao = eventDatabase.eventDao()

        var count by mutableIntStateOf(0)
        var favorites = mutableStateOf(emptySet<String>())

        init {
            eventDao.getAll().forEach() {
                favorites.value = favorites.value.plus(it.objectId)
            }
        }

        fun requestCount(districtState: DistrictState) {
            viewModelScope.launch {
                if (districtState is DistrictState.NEARBY && districtState.maxDistance < NEARBY_MAX_DISTANCE) {
                    count = 0
                    return@launch
                }
                watchedAt.getFlow(EventModule.preferenceWatchedAt(districtState))
                    .stateIn(viewModelScope)
                    .collectLatest { date ->
                        count =
                            if (date != null) {
                                eventRepository.getNewEventCount(
                                    watchedAt = date,
                                    districtState = districtState,
                                )
                            } else {
                                0
                            }
                    }
            }
        }

        suspend fun updateWatchedAt(districtState: DistrictState) {
            if (districtState !is DistrictState.NEARBY || districtState.maxDistance == NEARBY_MAX_DISTANCE) {
                watchedAt.set(
                    Date(),
                    EventModule.preferenceWatchedAt(districtState),
                )
            }
        }

        fun toggleEvent(event: Event) {
            if (favorites.value.contains(event.objectId)) {
                removeEvent(event)
            } else {
                addEvent(event)
            }
        }

        private fun addEvent(event: Event) {
            val entity = EventEntity(objectId = event.objectId)
            eventDao.insert(entity)
            favorites.value = favorites.value.plus(event.objectId)
        }

        private fun removeEvent(event: Event) {
            val entity = EventEntity(objectId = event.objectId)
            eventDao.delete(entity)
            favorites.value = favorites.value.minus(event.objectId)
        }
    }
