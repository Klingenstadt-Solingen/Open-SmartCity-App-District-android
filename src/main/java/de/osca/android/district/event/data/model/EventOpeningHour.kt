package de.osca.android.district.event.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import de.osca.android.district.core.data.model.dateAttribute
import java.util.Date

@ParseClassName("EventOpeningHour")
class EventOpeningHour : ParseObject() {
    var startTime: Date by dateAttribute()
    val endTime: Date by dateAttribute()
}