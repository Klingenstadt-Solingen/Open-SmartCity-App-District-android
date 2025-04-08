package de.osca.android.district.project.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.util.replaceWith
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.project.data.boundary.ProjectContactRepository
import de.osca.android.district.project.data.boundary.ProjectPartnerRepository
import de.osca.android.district.project.data.boundary.ProjectRepository
import de.osca.android.district.project.data.model.Project
import de.osca.android.district.project.data.model.ProjectContact
import de.osca.android.district.project.data.model.ProjectPartner
import de.osca.android.district.project.data.model.ProjectPartnerCategory
import javax.inject.Inject

@HiltViewModel
open class ProjectDetailViewModel
    @Inject
    constructor(
        private val projectRepository: ProjectRepository,
        private val projectContactRepository: ProjectContactRepository,
        private val projectPartnerRepository: ProjectPartnerRepository,
    ) : ViewModel(),
        Loadable<Project> {
        override var loadingState by mutableStateOf<LoadingState<Project>>(LoadingState.Loading)
        var contacts = mutableStateListOf<ProjectContact>()
        var partners = mutableStateListOf<ProjectPartner>()
        var partnerMap = mutableStateMapOf<ProjectPartnerCategory, List<ProjectPartner>>()

        fun requestProject(objectId: String) {
            loadingStateScope {
                val project = projectRepository.getProjectById(objectId = objectId)
                contacts.replaceWith(projectContactRepository.getProjectContactsByProjectId(project.objectId))
                partners.replaceWith(projectPartnerRepository.getProjectPartnersByProjectId(project.objectId))

                createPartnerMap()
                finishLoading(project)
            }
        }

        private fun createPartnerMap() {
            val map = partnerMap.toMutableMap()
            map.clear()

            for (partner in partners) {
                val cat = partner.category
                if (cat != null) {
                    val list = map[cat] ?: mutableListOf()

                    map[cat] = list.plus(partner)
                }
            }

            partnerMap.clear()
            partnerMap.putAll(map)
        }
    }
