package de.osca.android.district.core.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.NEARBY_MAX_DISTANCE
import de.osca.android.district.core.data.Debouncer
import de.osca.android.district.core.data.boundaries.DistrictRepository
import de.osca.android.district.core.data.hasPermission
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.model.district.DistrictMode
import de.osca.android.district.core.data.model.district.DistrictState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DistrictViewModel
    @Inject
    constructor(
        private val districtRepository: DistrictRepository,
        val application: Application,
    ) : ViewModel(),
        Loadable<District> {
        override var loadingState by mutableStateOf<LoadingState<District>>(LoadingState.Loading)

        private var selectedDistrictId: String? = null
        var districtState by mutableStateOf<DistrictState>(DistrictState.ALL)
            private set
        var districtMode by mutableStateOf(DistrictMode.ALL)
            private set
        var selectedDistrict by mutableStateOf<District?>(null)
            private set

        private val distanceDebouncer = Debouncer(scope = viewModelScope)

        private val locationProvider: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(application)

        private var locationCallback: LocationCallback? = null

        private val locationRequest =
            LocationRequest
                .Builder(
                    Priority.PRIORITY_LOW_POWER,
                    10.seconds.inWholeMilliseconds,
                ).apply {
                    this.setMinUpdateDistanceMeters(10f)
                }.build()

        init {
            initFetch()
        }

        fun initFetch() {
            loadingStateScope {
                if (selectedDistrictId == null) {
                    val district = districtRepository.getFirstDistrict()
                    selectedDistrictId = district.objectId
                    selectedDistrict = district
                } else {
                    selectedDistrict = districtRepository.getDistrictById(selectedDistrictId!!)
                }

                updateDistrictMode(districtMode)
            }
        }

        fun updateSelectedDistrict(district: District) {
            selectedDistrict = district
            district.objectId?.let { objectId ->
                selectedDistrictId = objectId
                districtState = DistrictState.DISTRICT(district)
            }
        }

        fun updateDistrictMode(
            districtMode: DistrictMode,
            maxDistance: Float = NEARBY_MAX_DISTANCE,
        ) {
            this.districtMode = districtMode
            stopUpdateListener()
            districtState =
                when (districtMode) {
                    DistrictMode.DISTRICT ->
                        selectedDistrict?.let { DistrictState.DISTRICT(it) }
                            ?: DistrictState.ALL

                    DistrictMode.ALL -> DistrictState.ALL
                    DistrictMode.NEARBY -> {
                        startLocationListener()
                        DistrictState.NEARBY(LatLng(0.0, 0.0), maxDistance)
                    }
                }
        }

        @SuppressLint("MissingPermission")
        private fun startLocationListener() {
            // TODO(Alex): Request User Permission via Intent
            if (!locationProvider.hasPermission(application)) return

            stopUpdateListener()
            locationCallback =
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location = locationResult.lastLocation
                        if (location != null) {
                            updateLocation(LatLng(location.latitude, location.longitude))
                        }
                    }

                    override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                        if (!locationAvailability.isLocationAvailable) {
                            stopUpdateListener()
                        }
                    }
                }

            locationProvider
                .requestLocationUpdates(
                    locationRequest,
                    locationCallback!!,
                    Looper.getMainLooper(),
                ).addOnFailureListener {
                    stopUpdateListener()
                }
        }

        fun debouncedUpdateMaxdistance(maxDistance: Float = NEARBY_MAX_DISTANCE) {
            distanceDebouncer.debounce {
                if (districtMode == DistrictMode.NEARBY) {
                    updateDistrictMode(
                        DistrictMode.NEARBY,
                        maxDistance,
                    )
                }
            }
        }

        private fun updateLocation(location: LatLng) {
            val state = districtState
            if (state is DistrictState.NEARBY) {
                districtState = DistrictState.NEARBY(location, state.maxDistance)
            }
        }

        private fun stopUpdateListener() {
            locationCallback?.let {
                locationProvider.removeLocationUpdates(it)
                locationCallback = null
            }
        }
    }
