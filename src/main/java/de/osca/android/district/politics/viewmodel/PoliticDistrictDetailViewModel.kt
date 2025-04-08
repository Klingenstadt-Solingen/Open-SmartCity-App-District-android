package de.osca.android.district.politics.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.politics.data.model.PoliticOrganization
import de.osca.android.district.politics.data.repository.OrganizationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PoliticDistrictDetailViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
    ) : ViewModel(),
    Loadable<PoliticOrganization?> {

    override var loadingState by mutableStateOf<LoadingState<PoliticOrganization?>>(LoadingState.Loading)

    fun refreshOrganisation(id: String) {
        viewModelScope.launch {
            finishLoading(organizationRepository.findAllBy(0,1,districtId = id).first())
        }
    }
}
