package de.osca.android.district.politics.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/// A structure representing an individual with personal details and contact information.
@Serializable
data class  PoliticPerson(
    /// The unique identifier for the person.
    override var id: String = "",
    
    /// The last name of the person.
    var lastName: String = "",
    
    /// The first name of the person.
    var firstName: String = "",
    
    /// A list of titles associated with the person (e.g., Dr., Prof.).
    var title: MutableList<String> = arrayListOf(),
    
    /// The preferred form of address for the person.
    var formOfAddress: String? = null,
    
    /// The gender of the person, if provided.
    var gender: String? = null,
    
    /// A list of phone numbers associated with the person.
    var phone: List<String> = arrayListOf(),
    
    /// A list of email addresses associated with the person.
    var email: List<String> = arrayListOf(),
    
    /// A brief description of the person's life or background.
    var life: String? = null,
    
    /// The source of information about the person's life.
    var lifeSource: String? = null,
    
    /// A URL to more information about the person, if available.
    override var webUrl: String? = null,
    
    /// The timestamp of the last update made to the person's details.
    override var updatedAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
    
    /// The timestamp when the person was created in the system.
    override var createdAt: LocalDateTime = LocalDateTime(0,1,1,0,0,0,0),
): BaseModel {

    fun name(): String {
        var nameArray = emptyArray<String>()
        nameArray = nameArray.plus(title)
        val name = "${this.firstName} ${this.lastName}"
        formOfAddress?.let{
            nameArray = nameArray.plus(it)
        }
        nameArray = nameArray.plus(name)
        return nameArray.joinToString(" ")
    }
}

