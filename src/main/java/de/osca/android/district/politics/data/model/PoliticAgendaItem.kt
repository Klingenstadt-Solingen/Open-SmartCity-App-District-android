package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// A structure representing an agenda item on a meeting.
@Serializable
data class PoliticAgendaItem (
    /// The unique identifier of the agenda item.
    override var id: String = "",
    
    /// The name or title of the agenda item.
    var name: String = "",
    
    /// The order of the agenda item in the meeting.
    var order: Int = 0,
    
    /// The number assigned to the agenda item, if applicable.
    var number: String? = null,
    
    /// The start date and time of the agenda item.
    var startDateTime: LocalDateTime? = null,
    
    /// The end date and time of the agenda item.
    var endDateTime: LocalDateTime? = null,
    
    /// Indicates if the agenda item is public.
    var public: Boolean = false,
    
    /// The outcome or result of the agenda item discussion.
    var result: String? = null,
    
    /// The text of any resolution passed for the agenda item.
    var resolutionText: String? = null,
    
    /// The file associated with the resolution of the agenda item.
    var resolutionFile: PoliticFile? = null,
    
    /// The consultation paper for the agenda item, if applicable.
    var consultationPaper: PoliticConsultationPaper? = null,
    
    /// The Auxiliary Files for the agenda item, if applicable.
    var auxiliaryFiles: List<AuxiliaryFile> = arrayListOf(),
    
    /// The website URL for the agenda item, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update of the agenda item.
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the agenda item was created in the system.
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel
