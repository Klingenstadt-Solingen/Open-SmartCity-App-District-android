package de.osca.android.district.core.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StringDataStoreDelegate(
    private val dataStore: DataStore<Preferences>,
) {
    fun getFlow(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { it[key] }
    }

    suspend fun set(
        value: String?,
        key: Preferences.Key<String>,
    ) {
        dataStore.edit {
            if (value != null) {
                it[key] = value
            } else {
                it.remove(key)
            }
        }
    }
}
