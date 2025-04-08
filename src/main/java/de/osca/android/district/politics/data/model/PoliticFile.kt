package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

/// Represents a file with its metadata and access information.
@Serializable
data class  PoliticFile(
    /// The unique identifier for the file.
    @Required
    override var id: String = "",
    
    /// The MIME type of the file, indicating the format (e.g., 'image/png').
    var mimeType: String? = null,
    
    /// A URL to access the file directly.
    @Required
    var accessUrl: String = "",
    
    /// A URL for downloading the file, if available.
    var downloadUrl: String? = null,

    var name: String? = null,

    var fileName: String? = null,

    var text: String? = null,

    var date: LocalDate? = null,

    var size: Int? = null,

    var sha512Checksum: String? = null,
    
    /// A URL to more information about the file, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update made to the file's details.
    @Required
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the file was created in the system.
    @Required
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel
