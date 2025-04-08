package de.osca.android.district.mobility.data.model.pricing

import kotlinx.serialization.Serializable

@Serializable
data class PricingModel(
    val end: Int?,
    val interval: Int?,
    val rate: Int?,
    val start: Int?,
)
