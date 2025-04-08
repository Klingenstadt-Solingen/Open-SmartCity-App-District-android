package de.osca.android.district.project.data.model

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.doubleAttribute
import com.parse.ktx.delegates.relationAttribute
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.district.core.data.model.district.District
import de.osca.android.district.core.data.model.nullableDateAttribute
import java.util.Date

@ParseClassName("Project")
class Project : ParseObject() {
    val name: String by stringAttribute()
    val summary: String by stringAttribute()
    val content: String by stringAttribute()
    val image: String by stringAttribute()
    val url: String? by stringAttribute()
    val volume: Double? by doubleAttribute()
    val status: ProjectStatus? by attribute()
    val startDate: Date? by nullableDateAttribute()
    val endDate: Date? by nullableDateAttribute()
    val geopoint: ParseGeoPoint? by attribute()
    val partners: ParseRelation<ProjectPartner> by relationAttribute()
    val contacts: ParseRelation<ProjectContact> by relationAttribute()
    val districts: ParseRelation<District> by relationAttribute()
}
