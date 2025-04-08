package de.osca.android.district.project.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.data.model.DateDataStoreDelegate
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.project.ProjectModule
import de.osca.android.district.project.data.boundary.ProjectRepository
import de.osca.android.district.project.projectDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class ProjectViewModel
    @Inject
    constructor(
        private val projectRepository: ProjectRepository,
        application: Application,
    ) : ViewModel() {
        private var watchedAt = DateDataStoreDelegate(application.applicationContext.projectDataStore)

        var count by mutableIntStateOf(0)

        fun requestCount(districtState: DistrictState) {
            viewModelScope.launch {
                if (districtState is DistrictState.NEARBY && districtState.maxDistance < NEARBY_MAX_DISTANCE) {
                    count = 0
                    return@launch
                }
                watchedAt.getFlow(
                    ProjectModule.preferenceWatchedAt(districtState),
                )
                    .stateIn(viewModelScope)
                    .collectLatest { date ->
                        count =
                            if (date != null) {
                                projectRepository.getNewProjectCount(
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
                    ProjectModule.preferenceWatchedAt(districtState),
                )
            }
        }
    }
