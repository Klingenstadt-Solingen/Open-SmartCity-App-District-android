package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.nullableStringAttribute
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.district.core.data.model.dateAttribute
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.model.nullableDateAttribute
import java.util.Date

@ParseClassName("Event")
class Event : ParseObject() {
    var startDate: Date by dateAttribute()
    var endDate: Date? by nullableDateAttribute()
    var location: HashMap<String, Any>? by attribute()
    var name: String by stringAttribute()
    var category: String by stringAttribute()
    var image: String? by attribute()
    var url: String? by nullableStringAttribute()
    var description: String? by nullableStringAttribute()
    var offers: List<Any> by attribute()
    val geopoint: ParseGeoPoint by attribute()
    val districts: ParseRelation<District> by relationAttribute()
    var type: EventType? by attribute()
    val sponsors: ParseRelation<EventSponsor> by relationAttribute()
    val eventStatus: String by stringAttribute()
}
