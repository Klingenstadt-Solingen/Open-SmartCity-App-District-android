package de.osca.android.district.mobility.data.model

import de.osca.android.district.mobility.data.serializer.CoordinateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.locationtech.jts.geom.Coordinate

@Serializable
data class BusStation(
    override val id: String,
    override val name: String,
    override val information: String,
    @Serializable(CoordinateSerializer::class)
    override val geopoint: Coordinate = Coordinate(-0.386182,-160.0273),

    override val iconUrl: String? = null,
    override val address: String? = null,
    override val iosDeeplink: String? = null,
    override val androidDeeplink: String? = null,
    override val webUrl: String? = null,

    override var providerName: String? = null,

    val isWheelchairbordingAvailable: Boolean,
    val locationType: LocationType = LocationType.UNKNOWN,

    val stops: List<Stop> = emptyList(),

    override val updatedAt: LocalDateTime? = null,
) : Mobility
