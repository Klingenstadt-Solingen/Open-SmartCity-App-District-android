package de.osca.android.district.weather.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.R
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.StringDataStoreDelegate
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.weather.WeatherModule
import de.osca.android.district.weather.data.boundaries.WeatherRepository
import de.osca.android.district.weather.data.model.Weather
import de.osca.android.district.weather.weatherDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel
    @Inject
    constructor(
        private val repository: WeatherRepository,
        val application: Application,
    ) : ViewModel(),
        Loadable<Weather?> {
        override var loadingState by mutableStateOf<LoadingState<Weather?>>(LoadingState.Loading)

        private val weatherDateStore =
            StringDataStoreDelegate(application.applicationContext.weatherDataStore)

        var selectedWeatherId by mutableStateOf<String?>(null)

        init {
            loadingStateScope {
                weatherDateStore
                    .getFlow(WeatherModule.SELECTED_WEATHER_ID)
                    .collectLatest {
                        selectedWeatherId = it
                        if (it != null) {
                            finishLoading(repository.getWeatherById(it))
                        } else {
                            finishLoading(null)
                        }
                    }
            }
        }

        fun changeSelectedWeather(weather: Weather) {
            viewModelScope.launch {
                weatherDateStore.set(weather.objectId, WeatherModule.SELECTED_WEATHER_ID)
            }
            selectedWeatherId = weather.objectId
            finishLoading(weather)
        }

        fun getSelectedId(): String? {
            val state = loadingState
            if (state is LoadingState.Loaded) {
                state.data?.let {
                    return it.objectId
                }
            }
            return null
        }

        companion object {
            var preselectedMeasurementCategories =
                listOf(
                    "lufttemperatur",
                    "realtiver_luftdruck_avg",
                    "niederschlagsintensitaet_avg",
                    "windgeschwindigkeit_kmh_avg",
                    "windrichtung_avg",
                    "globalstrahlung_avg",
                )

            private val weatherIconDictionary =
                mapOf(
                    "Luftdruck" to R.drawable.ic_luftdruck,
                    "Niederschlag" to R.drawable.ic_cloud,
                    "Windgeschwindigkeit" to R.drawable.ic_windgeschwindigkeit,
                    "Windrichtung" to R.drawable.ic_windrichtung,
                    "Globalstrahlung" to R.drawable.ic_globalstrahlung,
                    "Temperatur" to R.drawable.ic_eisprozent,
                )

            fun hasWeatherRessourceIcon(text: String): Boolean = weatherIconDictionary.containsKey(text)

            fun getWeatherRessourceIcon(text: String): Int = weatherIconDictionary[text] ?: R.drawable.ic_cloud
        }
    }
