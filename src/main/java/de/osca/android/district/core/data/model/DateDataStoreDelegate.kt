package de.osca.android.district.core.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class DateDataStoreDelegate(
    private val dataStore: DataStore<Preferences>,
) {
    fun getFlow(key: Preferences.Key<Long>): Flow<Date?> =
        dataStore.data.map {
            val timeInMillis = it[key]
            timeInMillis?.let { dateLong ->
                Date(dateLong)
            }
        }

    suspend fun set(
        value: Date?,
        key: Preferences.Key<Long>,
    ) {
        dataStore.edit {
            if (value != null) {
                it[key] = value.time
            } else {
                it.remove(key)
            }
        }
    }
}
