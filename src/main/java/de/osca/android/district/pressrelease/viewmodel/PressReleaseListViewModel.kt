package de.osca.android.district.pressrelease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.paging.ParseGenericPagingSource
import de.osca.android.district.pressrelease.data.boundaries.PressReleaseRepository
import de.osca.android.district.pressrelease.data.model.PressRelease
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
import javax.inject.Inject

@HiltViewModel
class PressReleaseListViewModel
    @Inject
    constructor(
        private val repository: PressReleaseRepository,
    ) : ViewModel() {
        private var _districtState = MutableStateFlow<DistrictState>(DistrictState.ALL)
        val districtState: StateFlow<DistrictState> get() = _districtState

        private var _searchText = MutableStateFlow("")
        val searchText: StateFlow<String> get() = _searchText

        var pressReleases: Flow<PagingData<PressRelease>> = emptyFlow()

        init {
            viewModelScope.launch {
                @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
                pressReleases =
                    combine(
                        searchText.debounce(300),
                        districtState,
                    ) { searchText, districtState ->
                        Pager(
                            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                            pagingSourceFactory = {
                                ParseGenericPagingSource(
                                    onLoad = { skip, limit ->
                                        repository.getPressReleases(
                                            districtState,
                                            searchText,
                                            skip,
                                            limit,
                                        )
                                    },
                                )
                            },
                        ).flow
                    }.flatMapLatest { it }.cachedIn(viewModelScope)
            }
        }

        fun updateSearch(text: String) {
            _searchText.value = text
        }

        fun updateDistrict(district: DistrictState) {
            _districtState.value = district
        }
    }
