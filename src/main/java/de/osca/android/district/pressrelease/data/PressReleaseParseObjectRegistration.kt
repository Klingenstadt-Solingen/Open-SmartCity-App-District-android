package de.osca.android.district.pressrelease.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.pressrelease.data.model.PressRelease
import javax.inject.Inject

class PressReleaseParseObjectRegistration
    @Inject
    constructor() : ParseObjectRegistration {
        override fun registerSubclasses() {
            ParseObject.registerSubclass(PressRelease::class.java)
        }
    }
