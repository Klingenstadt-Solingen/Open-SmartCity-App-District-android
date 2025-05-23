package de.osca.android.district.politics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.paging.GenericPagingSource
import de.osca.android.district.politics.data.model.PoliticMembership
import de.osca.android.district.politics.data.repository.MembershipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PoliticMemberDetailViewModel
    @Inject
    constructor(
        private val membershipRepository: MembershipRepository,
    ) :  ViewModel() {

    private var _personId = MutableStateFlow<String>("")

    var memberships: Flow<PagingData<PoliticMembership>> = emptyFlow()

    init {
        viewModelScope.launch {
            memberships =
                Pager(
                    config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                    pagingSourceFactory = {
                        GenericPagingSource(
                            onLoad = { skip, limit ->
                                membershipRepository.findAllBy(
                                    skip,
                                    limit,
                                    null,
                                    personId = _personId.value
                                )
                            },
                        )
                    },
                ).flow.cachedIn(viewModelScope)
        }
    }

    fun updatePersonId(id: String) {
        _personId.value = id
    }

}
