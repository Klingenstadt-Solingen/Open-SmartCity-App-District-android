package de.osca.android.district.core.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    return locationManager?.run {
        isProviderEnabled(LocationManager.GPS_PROVIDER) || isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } ?: false
}

/*** Suppress Missing permission because Android Studio accuses MissingPermission
even though the permission is added to the Manifest
 ***/
@SuppressLint("MissingPermission")
fun Context.getLastDeviceLocation(onCompleted: (LatLng?) -> Unit) {
    val focusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                focusedLocationProviderClient.lastLocation
                focusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        val latLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                        onCompleted.invoke(latLng)
                    } else {
                        onCompleted.invoke(null)
                    }
                }
            }
        }
    }
}

/*** Suppress Missing permission because Android Studio accuses MissingPermission
even though the permission is added to the Manifest
 ***/
@SuppressLint("MissingPermission")
fun Context.getLastDeviceLocationOnce(onCompleted: (LatLng?) -> Unit) {
    val focusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                focusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        val latLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                        onCompleted.invoke(latLng)
                    } else {
                        onCompleted.invoke(null)
                    }
                }
            }
        }
    }
}
