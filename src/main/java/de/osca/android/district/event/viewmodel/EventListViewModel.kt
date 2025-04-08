package de.osca.android.district.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.paging.ParseGenericPagingSource
import de.osca.android.district.core.util.atEndOfDay
import de.osca.android.district.core.util.isSameDay
import de.osca.android.district.core.util.toDate
import de.osca.android.district.event.EventDatabase
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventFilter
import de.osca.android.district.event.presentation.components.tabs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EventListViewModel
    @Inject
    constructor(
        private val repository: EventRepository,
        eventDatabase: EventDatabase,
    ) : ViewModel() {
        private var eventDao = eventDatabase.eventDao()

        var showDatePicker by mutableStateOf(false)

        private var _selectedRangeMin = MutableStateFlow<LocalDateTime>(LocalDate.now().atStartOfDay())
        private var _selectedRangeMax = MutableStateFlow(LocalDate.now().atEndOfDay())
        var selectedRangeMin: StateFlow<LocalDateTime> = _selectedRangeMin
        var selectedRangeMax: StateFlow<LocalDateTime> = _selectedRangeMax

        private var _districtState = MutableStateFlow<DistrictState>(DistrictState.ALL)
        val districtState: StateFlow<DistrictState> get() = _districtState

        private var _searchText = MutableStateFlow("")
        val searchText: StateFlow<String> get() = _searchText

        var events: Flow<PagingData<Event>> = emptyFlow()

        private var _filterTypes = MutableStateFlow(setOf(EventFilter.DAY))
        var filterTypes: StateFlow<Set<EventFilter>> = _filterTypes

        init {
            viewModelScope.launch {
                @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
                events =
                    combine(
                        searchText.debounce(300),
                        districtState,
                        selectedRangeMin,
                        selectedRangeMax,
                        _filterTypes,
                    ) { searchText, districtState, min, max, filterTypes ->

                        var minDate: Date? = null
                        var maxDate: Date? = null

                        if (filterTypes.contains(EventFilter.DAY) || filterTypes.contains(EventFilter.TIMERANGE)) {
                            minDate = min.toDate()
                            maxDate = max.toDate()
                        }

                        Pager(
                            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                            pagingSourceFactory = {
                                var bookmarkIds: List<String>? = null
                                if (filterTypes.contains(EventFilter.BOOKMARKS)) {
                                    bookmarkIds =
                                        eventDao.getAll().map {
                                            it.objectId
                                        }
                                }

                                ParseGenericPagingSource(
                                    onLoad = { skip, limit ->
                                        repository.getEvents(
                                            districtState,
                                            minDate,
                                            maxDate,
                                            bookmarkIds,
                                            limit,
                                            skip,
                                            searchText,
                                        )
                                    },
                                )
                            },
                        ).flow
                    }.flatMapLatest { it }.cachedIn(viewModelScope)
            }
        }

        fun updateText(text: String) {
            _searchText.value = text
        }

        fun updateDistrict(district: DistrictState) {
            _districtState.value = district
        }

        private fun changeDates(
            start: LocalDateTime,
            end: LocalDateTime,
        ) {
            if (start != selectedRangeMin.value || end != selectedRangeMax.value) {
                _selectedRangeMin.value = start.toLocalDate().atStartOfDay()
                _selectedRangeMax.value = end.toLocalDate().atEndOfDay()
            }
        }

        fun selectDate(date: LocalDateTime) {
            addFilter(EventFilter.DAY)
            removeFilter(EventFilter.TIMERANGE)

            changeDates(date, date.toLocalDate().atEndOfDay())
        }

        fun selectRange(
            start: LocalDateTime,
            end: LocalDateTime,
        ) {
            addFilter(EventFilter.TIMERANGE)
            removeFilter(EventFilter.DAY)

            changeDates(start, end)
        }

        fun changeTab(tab: Int) {
            val date = getDateOfTab(tab)
            selectDate(date)
        }

        fun getDateOfTab(tab: Int): LocalDateTime = LocalDate.now().plusDays(tab.toLong()).atStartOfDay()

        fun addFilter(filter: EventFilter) {
            _filterTypes.value = _filterTypes.value.plus(filter)
        }

        fun removeFilter(filter: EventFilter) {
            _filterTypes.value = _filterTypes.value.minus(filter)
        }

        fun checkNoTabIsSelected(): Boolean {
            for (index in 0..tabs.lastIndex) {
                val date = getDateOfTab(index)
                if (date.isSameDay(listOf(_selectedRangeMin.value, _selectedRangeMax.value))) {
                    return false
                }
            }
            return true
        }
    }
