package de.osca.android.district.weather.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.parse.ParseGeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.paging.ParseGenericPagingSource
import de.osca.android.district.weather.data.boundaries.WeatherRepository
import de.osca.android.district.weather.data.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSelectionViewModel
    @Inject
    constructor(
        private val repository: WeatherRepository,
        application: Application,
    ) : ViewModel() {
        private var _districtState = MutableStateFlow<DistrictState>(DistrictState.ALL)
        val districtState: StateFlow<DistrictState> =
            _districtState.asStateFlow()
        var geopoint: ParseGeoPoint? = null

        var weathers by mutableStateOf<Flow<PagingData<Weather>>>(
            emptyFlow(),
        )

        private val locationProvider: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(application)

        init {
            if (ContextCompat.checkSelfPermission(
                    application.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    application.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationProvider.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        geopoint = ParseGeoPoint(it.latitude, it.longitude)
                    }
                    setWeatherFlow()
                }
            } else {
                setWeatherFlow()
            }
        }

        fun updateDistrict(districtState: DistrictState) {
            _districtState.value = districtState
        }

        private fun setWeatherFlow() {
            viewModelScope.launch {
                districtState.collectLatest { districtState ->
                    weathers =
                        Pager(
                            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                            pagingSourceFactory = {
                                ParseGenericPagingSource { skip, limit ->
                                    repository.getWeathers(
                                        districtState,
                                        skip,
                                        limit,
                                    )
                                }
                            },
                        ).flow.cachedIn(viewModelScope)
                }
            }
        }
    }
