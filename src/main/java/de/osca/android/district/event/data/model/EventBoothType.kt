package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("EventBoothType")
class EventBoothType : ParseObject() {
    var name: String by stringAttribute()
    var icon: ParseFile by attribute()
}
