package de.osca.android.district.pointofinterest.data.model

import kotlinx.serialization.Serializable

@Serializable
class PointOfInterestDetail(
    val type: String,
    val title: String,
    val subtitle: String?,
    val value: String?,
    val iconName: String?,
    val iconPath: String?,
    val iconMimetype: String?,
    val symbolName: String?,
    val symbolPath: String?,
    val symbolMimetype: String?,
    val position: Int,
    val filterField: String?,
) {
    fun getImageUrl(): String? {
        if (symbolPath != null && symbolName != null && symbolMimetype != null) {
            return "$symbolPath/$symbolName$symbolMimetype"
        }
        return null
    }

    fun getIconImageUrl(): String? {
        if (iconPath != null && iconName != null && iconMimetype != null) {
            return "$iconPath/$iconName$iconMimetype"
        }

        return null
    }

    fun getType(): PointOfInterestDetailType = PointOfInterestDetailType.fromString(type)
}
