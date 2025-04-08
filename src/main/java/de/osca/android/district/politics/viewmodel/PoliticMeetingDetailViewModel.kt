package de.osca.android.district.politics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.paging.GenericPagingSource
import de.osca.android.district.politics.data.model.PoliticAgendaItem
import de.osca.android.district.politics.data.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PoliticMeetingDetailViewModel
    @Inject
    constructor(
        private val agendaRepository: AgendaRepository,
    ) : ViewModel() {

    private var _meetingId = MutableStateFlow("")

    var agendaItems: Flow<PagingData<PoliticAgendaItem>> = emptyFlow()

    init {
        viewModelScope.launch {
            agendaItems =
                Pager(
                    config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                    pagingSourceFactory = {
                        GenericPagingSource(
                            onLoad = { skip, limit ->
                                agendaRepository.findAllBy(skip,
                                    limit, meetingId = _meetingId.value)
                            },
                        )
                    },
                ).flow.cachedIn(viewModelScope)
        }
    }

    fun updateMeetingId(id: String) {
        _meetingId.value = id
    }

}
