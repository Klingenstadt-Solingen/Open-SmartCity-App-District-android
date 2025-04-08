package de.osca.android.district.project.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("ProjectContact")
class ProjectContact : ParseObject() {
    val name: String by stringAttribute()
    val organization: String by stringAttribute()
    val phoneNumber: String by stringAttribute()
    val image: String by stringAttribute()
    val email: String by stringAttribute()
}
