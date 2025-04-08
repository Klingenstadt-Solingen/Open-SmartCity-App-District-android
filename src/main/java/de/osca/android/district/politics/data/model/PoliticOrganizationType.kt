package de.osca.android.district.politics.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/// Defines the different types of organizations.
@Serializable
enum class PoliticOrganizationType(type: String) {
    /// A committee or governing body.
    @SerialName("COMMITTEE")
    COMMITTEE("COMMITTEE"),
    
    /// A political party.
    @SerialName("PARTY")
    PARTY("PARTY"),
    
    /// A faction or group within a legislative body.
    @SerialName("FACTION")
    FACTION("FACTION"),
    
    /// An administrative area or division.
    @SerialName("ADMINISTRATIVE_AREA")
    ADMINISTRATIVE_AREA("ADMINISTRATIVE_AREA"),
    
    /// An external committee or organization.
    @SerialName("EXTERNAL_COMMITTEE")
    EXTERNAL_COMMITTEE("EXTERNAL_COMMITTEE"),
    
    /// An institution, such as an organization or establishment.
    @SerialName("INSTITUTION")
    INSTITUTION("INSTITUTION"),
    
    /// Other types of organizations not classified above.
    @SerialName("OTHER")
    OTHER("OTHER"),
    
    /// Unknown type of organization.
    @SerialName("UNKNOWN")
    UNKNOWN("UNKNOWN"),
}
