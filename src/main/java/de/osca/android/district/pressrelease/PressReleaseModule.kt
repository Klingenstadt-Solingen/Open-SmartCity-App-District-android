package de.osca.android.district.pressrelease

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
import de.osca.android.district.pressrelease.data.PressReleaseParseObjectRegistration
import de.osca.android.district.pressrelease.data.boundaries.PressReleaseRepository
import de.osca.android.district.pressrelease.data.repository.PressReleaseRepositoryImpl

val Context.pressReleaseDataStore: DataStore<Preferences> by preferencesDataStore(name = "press_release_preferences")

@Module
@InstallIn(SingletonComponent::class)
abstract class PressReleaseModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: PressReleasePlugin): Plugin

    @Binds
    abstract fun bindsPressReleaseRepository(repository: PressReleaseRepositoryImpl): PressReleaseRepository

    @Binds
    @IntoSet
    abstract fun bindsPressReleaseParseObjectRegistration(registration: PressReleaseParseObjectRegistration): ParseObjectRegistration

    companion object {
        fun preferenceWatchedAt(districtState: DistrictState): Preferences.Key<Long> {
            return when (districtState) {
                is DistrictState.DISTRICT -> longPreferencesKey("press_release_watched_at_district_" + districtState.district.objectId)
                else -> longPreferencesKey("press_release_watched_at_all")
            }
        }
    }
}
