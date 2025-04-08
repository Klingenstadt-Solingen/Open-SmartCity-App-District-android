package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// Represents a consultation paper, including its metadata and related files.
@Serializable
data class PoliticConsultationPaper (
    /// The unique identifier for the consultation paper.
    override var id: String = "",
    
    /// The name or title of the consultation paper.
    var name: String = "",
    
    /// A reference or identifier associated with the consultation paper.
    var reference: String? = null,
    
    /// Date which is used as the starting point for deadlines etc.
    var date: LocalDate? = null,
    
    /// The type of consultation paper (e.g., 'report', 'statement').
    var paperType: String? = null,
    
    /// Indicates whether the consultation paper is authoritative.
    var authoritative: Boolean = false,
    
    /// The role or purpose of the consultation paper in its context.
    var role: String? = null,
    
    /// The main file associated with the consultation paper, if available.
    var mainFile: PoliticFile? = null,
    
    /// A URL to more information about the consultation paper, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update made to the consultation paper's details.
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the consultation paper was created in the system.
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel
