package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

/// A structure representing a meeting with its details.
@Serializable
data class PoliticMeeting (
    /// The unique identifier of the meeting.
    @Required
    override var id: String = "",
    
    /// The name or title of the meeting.
    @Required
    var name: String = "",
    
    /// The start date and time of the meeting.
    var startDateTime: LocalDateTime? = null,
    
    /// The end date and time of the meeting.
    var endDateTime: LocalDateTime? = null,
    
    /// The location where the meeting takes place.
    var location: PoliticLocation? = null,
    
    /// The current state of the meeting.
    var meetingState: PoliticMeetingState? = null,
    
    /// The file associated with the meeting invitation.
    var invitationFile: PoliticFile? = null,
    
    /// The file containing the results protocol of the meeting.
    var resultsProtocolFile: PoliticFile? = null,
    
    /// The file containing the verbatim protocol of the meeting.
    var verbatimProtocolFile: PoliticFile? = null,
        
    /// The website URL for the meeting, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update of the meeting details.
    @Required
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the meeting was created in the system.
    @Required
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
) : BaseModel
