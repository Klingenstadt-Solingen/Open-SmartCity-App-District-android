package de.osca.android.district.weather.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.StringDataStoreDelegate
import de.osca.android.district.weather.WeatherModule
import de.osca.android.district.weather.data.boundaries.WeatherRepository
import de.osca.android.district.weather.data.model.WeatherMeasurement
import de.osca.android.district.weather.weatherDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
    @Inject
    constructor(
        private val repository: WeatherRepository,
        val application: Application,
    ) : ViewModel() {
        private val weatherDataStore =
            StringDataStoreDelegate(application.applicationContext.weatherDataStore)
        var measurement by mutableStateOf<WeatherMeasurement?>(null)

        init {
            viewModelScope.launch {
                weatherDataStore
                    .getFlow(WeatherModule.SELECTED_WEATHER_ID)
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
                    .collectLatest {
                        measurement =
                            if (it != null) {
                                val measurements = repository.getWeatherById(it).getValues()
                                measurements.find { weatherMeasurement ->
                                    weatherMeasurement.name == "Temperatur"
                                }
                            } else {
                                null
                            }
                    }
            }
        }
    }
