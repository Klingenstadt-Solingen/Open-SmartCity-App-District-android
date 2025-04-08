package de.osca.android.district.project.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.paging.ParseGenericPagingSource
import de.osca.android.district.core.util.replaceWith
import de.osca.android.district.project.data.boundary.ProjectRepository
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class ProjectGridViewModel
    @Inject
    constructor(
        private val projectRepository: ProjectRepository,
    ) : ViewModel() {
        private val _searchText = MutableStateFlow("")
        val searchText = _searchText.asStateFlow()

        private val _projectStatus = MutableStateFlow<ProjectStatus?>(null)
        val projectStatus = _projectStatus.asStateFlow()

        private val _districtState = MutableStateFlow<DistrictState>(DistrictState.ALL)
        val districtState = _districtState.asStateFlow()

        var projects: Flow<PagingData<Project>> = emptyFlow()

        val statusList = mutableStateListOf<ProjectStatus>()

        init {
            viewModelScope.launch {
                @OptIn(FlowPreview::class)
                projects =
                    combine(
                        searchText.debounce(300),
                        projectStatus,
                        districtState,
                    ) { searchText, projectStatus, districtState ->
                        Pager(
                            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                            pagingSourceFactory = {
                                ParseGenericPagingSource(
                                    onLoad = { skip, limit ->

                                        projectRepository.getProjects(
                                            projectStatus,
                                            skip,
                                            limit,
                                            searchText,
                                            districtState,
                                        )
                                    },
                                )
                            },
                        ).flow
                    }.flatMapLatest { it }.cachedIn(viewModelScope)

                // Putting this call above the projects flow will block the flow.
                requestStatusList()
            }
        }

        private suspend fun requestStatusList() {
            statusList.replaceWith(projectRepository.getProjectStatusList())
        }

        fun setDistrictState(value: DistrictState) {
            _districtState.value = value
        }

        fun setSearchText(value: String) {
            _searchText.value = value
        }

        fun setProjectStatus(value: ProjectStatus?) {
            _projectStatus.value = value
        }
    }
