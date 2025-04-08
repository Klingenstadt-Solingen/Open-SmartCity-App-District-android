package de.osca.android.district.pointofinterest.presentation.component.map

import com.google.android.gms.maps.model.UrlTileProvider
import java.net.URL
import kotlin.math.pow

/**
 * For more info about TileProvider, see https://developers.google.com/maps/documentation/android-sdk/tileoverlay
 *
 */
class PoiTileOverlay : UrlTileProvider(WIDTH, HEIGHT) {
    companion object {
        private const val WIDTH = 256
        private const val HEIGHT = 256

        private const val MAP_SIZE = 20037508.34789244 * 2
        private const val ORIG_X = -20037508.34789244
        private const val ORIG_Y = 20037508.34789244
    }

    // TODO: inject from settings maybe?
    private val url =
        "https://geodaten.metropoleruhr.de/spw2?SERVICE=WMS&VERSION=1.3.0&REQUEST=GetMap&FORMAT=image%2Fpng&LAYERS=spw2_light_grundriss&STYLES=&CRS=EPSG:3857&WIDTH=$WIDTH&HEIGHT=$HEIGHT&BBOX="

    override fun getTileUrl(
        x: Int,
        y: Int,
        z: Int,
    ): URL {
        val x = x.toDouble()
        val y = y.toDouble()

        val tileSize = MAP_SIZE / 2.0.pow(z.toDouble())
        val minx = ORIG_X + x * tileSize
        val maxx = ORIG_X + (x + 1) * tileSize
        val miny = ORIG_Y - (y + 1) * tileSize
        val maxy = ORIG_Y - y * tileSize

        return getUrl(minx, miny, maxx, maxy)
    }

    private fun getUrl(
        minx: Double,
        miny: Double,
        maxx: Double,
        maxy: Double,
    ): URL = URL("$url$minx,$miny,$maxx,$maxy")
}
