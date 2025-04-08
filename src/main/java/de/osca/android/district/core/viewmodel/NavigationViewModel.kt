package de.osca.android.district.core.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.plugin.PluginManager
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel
    @Inject
    constructor(
        private val pluginManager: PluginManager,
    ) : ViewModel() {
        fun getNavGraphDestinations() = pluginManager.getNavGraphDestinations()
    }
