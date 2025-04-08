package de.osca.android.district.mobility.data.model

import de.osca.android.district.mobility.data.model.pricing.PricingPlan
import de.osca.android.district.mobility.data.serializer.CoordinateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.locationtech.jts.geom.Coordinate


@Serializable
data class Vehicle(
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

    var description: String? = null,
    val imageUrl: String? = null,
    val color: String? = null,
    val secondaryColor: String? = null,

    // TODO: move in Sub Dto VehicleDetail
    var propulsionType: PropulsionType = PropulsionType.UNKNOWN,
    val plateNumber: String? = null,
    val batteryPercentage: Int? = null,
    val remainingRangeInMeter: Int? = null,

    val pricingPlan: PricingPlan? = null,

    override val updatedAt: LocalDateTime? = null
) : Mobility
