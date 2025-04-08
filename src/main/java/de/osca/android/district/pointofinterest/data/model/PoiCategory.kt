package de.osca.android.district.pointofinterest.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.booleanAttribute
import com.parse.ktx.delegates.doubleAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("POICategory")
class PoiCategory : ParseObject() {
    val name: String by stringAttribute()
    val sourceId: String by stringAttribute()
    val position: Double by doubleAttribute()
    val showCategory: Boolean by booleanAttribute()

    val symbolPath: String by stringAttribute()
    val symbolName: String by stringAttribute()
    val symbolMimetype: String by stringAttribute()

    val iconPath: String by stringAttribute()
    val iconName: String by stringAttribute()
    val iconMimetype: String by stringAttribute()

    fun getImageUrl(): String {
        return "$symbolPath/$symbolName$symbolMimetype"
    }

    fun getIconImageUrl(): String {
        return "$iconPath/$iconName$iconMimetype"
    }
}
