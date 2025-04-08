package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDateTime

interface BaseModel{
    /// unique ID.
    var id: String
    
    /// URL, to the Web-Object, if exists
    var webUrl: String?
    
    /// Timestanp when last updated
    var updatedAt: LocalDateTime
    
    /// Timestanp when created
    var createdAt: LocalDateTime
}


