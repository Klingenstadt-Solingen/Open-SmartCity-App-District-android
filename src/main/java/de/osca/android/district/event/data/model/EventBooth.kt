package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParsePolygon
import com.parse.ParseRelation
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EventBooth")
class EventBooth : ParseObject() {
    var name: String by stringAttribute()
    val geopoint: ParseGeoPoint by attribute()
    val locationDescription: String by stringAttribute()
    val area: ParsePolygon by attribute()
    val content: String? by stringAttribute()
    val type: EventBoothType? by attribute()
    val tags: ParseRelation<EventTag> by relationAttribute()
    val mainSponsor: EventSponsor? by attribute()
    val sponsors: ParseRelation<EventSponsor> by relationAttribute()
}
