package de.osca.android.district.mobility.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Stop(
    val id: String,
    val name: String = "Keine informationen zum Namen vorhanden",
    val information: String = "Keine informationen vorhanden",

    val plateNumber: String?,

    var description: String? = "Keine Beschreibung vorhanden",

    val updatedAt: String? = null
)
