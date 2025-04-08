package de.osca.android.district.mobility.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.locationtech.jts.geom.Coordinate


@Serializable
sealed interface Mobility {
    val id: String
    val name: String
    val information: String
    val geopoint: Coordinate

    var providerName: String?
    val iconUrl: String?
    val address: String?
    val iosDeeplink: String?
    val androidDeeplink: String?
    val webUrl: String?
    val updatedAt: LocalDateTime?
}
