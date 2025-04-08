package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EventTag")
class EventTag : ParseObject() {
    var name: String by stringAttribute()
}
