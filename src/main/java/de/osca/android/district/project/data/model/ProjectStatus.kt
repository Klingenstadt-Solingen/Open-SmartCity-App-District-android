package de.osca.android.district.project.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.district.R

@ParseClassName("ProjectStatus")
class ProjectStatus : ParseObject() {
    val title: String by stringAttribute()

    fun getStringResource(): Int {
        return when (title) {
            "idea_status" -> R.string.idea_status
            "planning_status" -> R.string.planning_status
            "realization_status" -> R.string.realization_status
            "completed_status" -> R.string.completed_status
            else -> R.string.unknown_status
        }
    }
}
