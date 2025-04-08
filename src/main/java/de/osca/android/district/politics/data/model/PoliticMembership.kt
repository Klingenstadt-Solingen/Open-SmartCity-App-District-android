package de.osca.android.district.politics.data.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// A structure representing a membership within an organization.
@Serializable
data class PoliticMembership(
    /// The unique identifier of the membership.
    override var id: String = "",
    
    /// The role of the member within the organization, if applicable.
    var role: String? = null,
    
    /// Indicates if the member is a mayor.
    var mayor: Boolean = false,
    
    /// Indicates if the member has voting rights.
    var votingRight: Boolean = false,
    
    /// The date when the membership started.
    var startDate: LocalDate? = null,
    
    /// The date when the membership ended, if applicable.
    var endDate: LocalDate? = null,
    
    /// The person associated with the membership.
    var person: PoliticPerson = PoliticPerson(),
    
    /// The organization or entity on behalf of which the member acts, if applicable.
    var onBehalfOf: String? = null,

    var organizationName: String? = null,

    /// The website URL for the membership, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update of the membership details.
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the membership was created in the system.
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel
