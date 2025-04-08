package de.osca.android.district.core.data

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun FusedLocationProviderClient.hasPermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(
        context,
        ACCESS_FINE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

@RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
suspend fun FusedLocationProviderClient.awaitLastLocation(): LatLng? =
    suspendCancellableCoroutine { cor ->
        var loc: LatLng? = null

        lastLocation
            .addOnSuccessListener { location ->
                location.let {
                    loc = LatLng(location.latitude, location.longitude)
                }
            }.addOnFailureListener { e ->
                cor.resumeWithException(e)
            }

        cor.resume(loc)
    }
