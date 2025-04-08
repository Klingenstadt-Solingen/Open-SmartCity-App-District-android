package de.osca.android.district.mobility.data.model

import kotlinx.serialization.Serializable

@Serializable
data class  MobilityTypeDto(
    val id: String,
    val iconUrl: String
)

enum class MobilityType {
    BUS, SCOOTER, BICYCLE, CAR, TAXI
}
