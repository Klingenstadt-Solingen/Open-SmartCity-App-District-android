package de.osca.android.district.event.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.event.data.model.Event
import de.osca.android.district.event.data.model.EventBooth
import de.osca.android.district.event.data.model.EventBoothType
import de.osca.android.district.event.data.model.EventOpeningHour
import de.osca.android.district.event.data.model.EventSponsor
import de.osca.android.district.event.data.model.EventTag
import de.osca.android.district.event.data.model.EventType
import javax.inject.Inject

class EventParseObjectRegistration
    @Inject
    constructor() : ParseObjectRegistration {
        override fun registerSubclasses() {
            ParseObject.registerSubclass(Event::class.java)
            ParseObject.registerSubclass(EventBooth::class.java)
            ParseObject.registerSubclass(EventBoothType::class.java)
            ParseObject.registerSubclass(EventType::class.java)
            ParseObject.registerSubclass(EventTag::class.java)
            ParseObject.registerSubclass(EventSponsor::class.java)
            ParseObject.registerSubclass(EventOpeningHour::class.java)
        }
    }
