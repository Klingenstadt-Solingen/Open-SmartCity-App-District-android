package de.osca.android.district.project.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.district.R

@ParseClassName("ProjectPartnerCategory")
class ProjectPartnerCategory : ParseObject() {
    val title: String by stringAttribute()

    fun getStringResource(): Int {
        return when (title) {
            "cooperation_partners" -> R.string.cooperation_partners
            "funding_partners" -> R.string.funding_partners
            else -> R.string.unknown_partners
        }
    }
}
