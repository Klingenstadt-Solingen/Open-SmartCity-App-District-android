package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EventSponsor")
class EventSponsor : ParseObject() {
    var name: String by stringAttribute()
    val icon: ParseFile by attribute()
}
