package de.osca.android.district.project.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("ProjectPartner")
class ProjectPartner : ParseObject() {
    val name: String by stringAttribute()
    val image: String by stringAttribute()
    val url: String by stringAttribute()
    val category: ProjectPartnerCategory? by attribute()
}
