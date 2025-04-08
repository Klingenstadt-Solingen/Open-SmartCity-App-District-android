package de.osca.android.district.mobility.data.model.pricing

import kotlinx.serialization.Serializable

@Serializable
data class PricingPlan (
    val currency: String,
    val description: String,
    val isTaxable: Boolean,
    val name: String,
    val price: Float,
    val surgePricing: Boolean?,
    val url: String?,
    val perKmPricing: List<PricingModel>,
    val perMinPricing: List<PricingModel>
)
