package de.osca.android.district.politics.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/// Defines the various states of a meeting.
@Serializable
enum class PoliticMeetingState(state: String) {
    /// Meeting is scheduled but not yet started.
    @SerialName("SCHEDULED")
    SCHEDULED("SCHEDULED"),
    
    /// Invitations have been sent for the meeting.
    @SerialName("INVITED")
    INVITED("INVITED"),
    
    /// Meeting has been conducted.
    @SerialName("PERFORMED")
    PERFORMED("PERFORMED"),
    
    /// Meeting has been cancelled.
    @SerialName("CANCELLED")
    CANCELLED("CANCELLED"),
    
    /// Meeting state is not specified or available.
    @SerialName("NONE")
    NONE("NONE"),
    
    /// Unknown state of the meeting.
    @SerialName("UNKNOWN")
    UNKNOWN("UNKNOWN"),
}