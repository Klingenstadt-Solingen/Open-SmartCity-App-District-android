package de.osca.android.district.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.plugin.PluginManager
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(pluginManager: PluginManager) : ViewModel() {
        val widgets = pluginManager.getDashboardWidgets()
        val tiles = pluginManager.getDashboardTiles()
    }
