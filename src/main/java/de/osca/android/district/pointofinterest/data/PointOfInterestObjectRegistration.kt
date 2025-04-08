package de.osca.android.district.pointofinterest.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.pointofinterest.data.model.PoiCategory
import javax.inject.Inject

class PointOfInterestObjectRegistration
    @Inject
    constructor() : ParseObjectRegistration {
        override fun registerSubclasses() {
            ParseObject.registerSubclass(PoiCategory::class.java)
        }
    }
