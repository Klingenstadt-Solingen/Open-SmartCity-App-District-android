package de.osca.android.district.event.data.repository

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MarkerIconCache(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val width: Int = 60,
    private val height: Int = 90,
) {
    private val imageLoader =
        ImageLoader.Builder(context).build()
    private val bitmaps = HashMap<String, MutableStateFlow<BitmapDescriptor>>()

    fun getMarkerFlow(url: String): MutableStateFlow<BitmapDescriptor> {
        bitmaps[url]?.let {
            return it
        }

        bitmaps[url] =
            MutableStateFlow(
                BitmapDescriptorFactory.defaultMarker(),
            )
        coroutineScope.launch {
            requestDescriptor(url)
        }
        return bitmaps[url]!!
    }

    private suspend fun requestDescriptor(url: String) {
        val request =
            ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false)
                .build()
        when (val result = imageLoader.execute(request)) {
            is SuccessResult -> {
                val bitmap = result.drawable.toBitmap()
                val sizedBitmap =
                    bitmap.copy(bitmap.config, true).scale(width, height)
                bitmaps[url]?.let { bitmapFlow ->
                    bitmapFlow.value = BitmapDescriptorFactory.fromBitmap(sizedBitmap)
                }
            }

            else -> {}
        }
    }
}
