package de.osca.android.district.event.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey val objectId: String,
)
