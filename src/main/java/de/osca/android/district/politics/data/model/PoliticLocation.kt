package de.osca.android.district.politics.data.model
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

/// A structure representing a physical location.
@Serializable
data class PoliticLocation (
    /// The unique identifier of the location.
    @Required
    override var id: String = "",
    
    /// The room number or name within the location, if applicable.
    var room: String? = null,
    
    /// Additional details or notes about the location.
    var description: String? = null,
    
    /// The full street address of the location.
    var streetAddress: String? = null,
    
    /// The postal or ZIP code of the location.
    var postalCode: String? = null,
    
    /// The city or locality where the location is situated.
    var locality: String? = null,
    
    /// The sub-locality or district within the city, if applicable.
    var subLocality: String? = null,
    
    /// The website URL related to the location, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update of the location details.
    @Required
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the location was created in the system.
    @Required
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
) : BaseModel
