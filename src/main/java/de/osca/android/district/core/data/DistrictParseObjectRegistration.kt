package de.osca.android.district.core.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.data.model.district.District

class DistrictParseObjectRegistration : ParseObjectRegistration {
    override fun registerSubclasses() {
        ParseObject.registerSubclass(District::class.java)
    }
}
