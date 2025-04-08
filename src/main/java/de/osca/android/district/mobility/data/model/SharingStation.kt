package de.osca.android.district.mobility.data.model

import de.osca.android.district.mobility.data.serializer.CoordinateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.locationtech.jts.geom.Coordinate


@Serializable
data class SharingStation(
    override val id: String,
    override val name: String,
    override val information: String,
    @Serializable(CoordinateSerializer::class)
    override val geopoint: Coordinate = Coordinate(-0.386182,-160.0273),

    override val iconUrl: String?,
    override val address: String?,
    override val iosDeeplink: String?,
    override val androidDeeplink: String?,
    override val webUrl: String?,
    override var providerName: String?,

    val shortName: String?,
    val vehicles: List<Vehicle> = emptyList(),
    val parkingType: ParkingType = ParkingType.UNKNOWN,
    val parkingSpaceAmount: Int?,
    val availableVehicleAmount: Int?,

    override val updatedAt: LocalDateTime? = null
) : Mobility
