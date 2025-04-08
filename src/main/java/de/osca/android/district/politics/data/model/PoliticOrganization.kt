package de.osca.android.district.politics.data.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// A structure representing a political organization.
@Serializable
data class PoliticOrganization(
    /// The unique identifier of the organization.
    override var id: String = "",
    
    /// The full name of the organization.
    var name: String = "",
    
    /// The short name or abbreviation of the organization, if applicable.
    var shortName: String? = null,
    
    /// The date when the organization was established.
    var startDate: LocalDate? = null,
    
    /// The date when the organization ceased operations, if applicable.
    var endDate: LocalDate? = null,
    
    /// The total number of members in the organization.
    var memberCount: Int = 0,
    
    /// The number of voting members within the organization.
    var votingMemberCount: Int = 0,
    
    /// The most recent mayor associated with the organization, if applicable.
    var newestMayor: PoliticMembership? = null,
    
    /// The location associated with the organization.
    var location: PoliticLocation? = null,
    
    /// The type of the organization.
    var organizationType: PoliticOrganizationType = PoliticOrganizationType.UNKNOWN,
    
    /// The ID of the parent organization, if this is a sub-organization.
    var subOrganizationOf: String? = null,
    
    /// The classification of the organization, if applicable.
    var classification: String? = null,
    
    /// The official website of the organization, if available.
    var website: String? = null,
    
    /// The URL of the organization for web access.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update of the organization details.
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the organization was created in the system.
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel
