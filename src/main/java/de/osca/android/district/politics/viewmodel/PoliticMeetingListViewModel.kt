package de.osca.android.district.politics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.paging.GenericPagingSource
import de.osca.android.district.politics.data.model.PoliticMeeting
import de.osca.android.district.politics.data.repository.MeetingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
open class PoliticMeetingListViewModel
    @Inject
    constructor(
        private val meetingRepository: MeetingRepository,
    ) :  ViewModel() {

    private var _organizationId = MutableStateFlow<String>("")

    private var _future = MutableStateFlow(false)
    val future: StateFlow<Boolean> get() = _future

    var meetings: Flow<PagingData<PoliticMeeting>> = emptyFlow()

    init {
        viewModelScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            meetings =
                combine(
                    future,
                    _organizationId,
                ) { future, organizationId ->
                    Pager(
                        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                        pagingSourceFactory = {
                            GenericPagingSource(
                                onLoad = { skip, limit ->
                                    meetingRepository.findAllTill(
                                        skip,
                                        limit,
                                        organizationId = organizationId,
                                        if(!future) Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) else null
                                    )
                                },
                            )
                        },
                    ).flow
                }.flatMapLatest { it }.cachedIn(viewModelScope)
        }
    }

    fun updateOrganizationId(id: String) {
        _organizationId.value = id
    }

    fun updateFuture(state: Boolean) {
        _future.value = state
    }
}
