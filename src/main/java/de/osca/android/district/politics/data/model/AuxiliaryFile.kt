package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// Represents a file with its metadata and access information.
//data
@Serializable
data class AuxiliaryFile(
    var name: String? = null,
    var fileName: String = "",
    var text: String = "",
    var date: LocalDate = LocalDate(0,1,1),
    var size: Int = 0,
    var sha512Checksum: String = "",
    var mimeType: String? = "",
    var accessUrl: String = "",
    var downloadUrl: String? ="",
    override var id: String = "",
    override var webUrl: String? = "",
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel

