package de.osca.android.district.event.data.boundaries

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import de.osca.android.district.event.data.model.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<EventEntity>

    @Query("SELECT * FROM event WHERE objectId IN (:objectIds)")
    fun loadAllByIds(objectIds: List<String>): List<EventEntity>

    @Insert
    fun insert(vararg event: EventEntity)

    @Delete
    fun delete(event: EventEntity)
}
