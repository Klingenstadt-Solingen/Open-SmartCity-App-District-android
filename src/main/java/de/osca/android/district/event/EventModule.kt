package de.osca.android.district.event

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.data.model.district.DistrictState
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.event.data.EventParseObjectRegistration
import de.osca.android.district.event.data.boundaries.EventRepository
import de.osca.android.district.event.data.repository.EventRepositoryImpl

val Context.eventDataStore: DataStore<Preferences> by preferencesDataStore(name = "event_preferences")

@Module
@InstallIn(SingletonComponent::class)
abstract class EventModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: EventPlugin): Plugin

    @Binds
    abstract fun providesEventRepository(eventRepository: EventRepositoryImpl): EventRepository

    @Binds
    @IntoSet
    abstract fun eventParseObjectRegistration(parseObjectRegistration: EventParseObjectRegistration): ParseObjectRegistration


    companion object {
        fun preferenceWatchedAt(districtState: DistrictState): Preferences.Key<Long> {
            return when (districtState) {
                is DistrictState.DISTRICT -> longPreferencesKey("event_watched_at_district_" + districtState.district.objectId)
                else -> longPreferencesKey("event_watched_at_all")
            }
        }
    }
}
