package de.osca.android.district.project

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
import de.osca.android.district.project.data.ProjectParseObjectRegistration
import de.osca.android.district.project.data.boundary.ProjectContactRepository
import de.osca.android.district.project.data.boundary.ProjectPartnerRepository
import de.osca.android.district.project.data.boundary.ProjectRepository
import de.osca.android.district.project.data.repository.ProjectContactRepositoryImpl
import de.osca.android.district.project.data.repository.ProjectPartnerRepositoryImpl
import de.osca.android.district.project.data.repository.ProjectRepositoryImpl

val Context.projectDataStore: DataStore<Preferences> by preferencesDataStore(name = "project_preferences")

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectModule {
    @IntoSet
    @Binds
    abstract fun providesProjectPlugin(plugin: ProjectPlugin): Plugin

    @IntoSet
    @Binds
    abstract fun providesProjectParseObjectRegistration(parseObjectRegistration: ProjectParseObjectRegistration): ParseObjectRegistration

    @Binds
    abstract fun providesProjectRepository(projectRepository: ProjectRepositoryImpl): ProjectRepository

    @Binds
    abstract fun providesProjectContactRepository(projectContactRepository: ProjectContactRepositoryImpl): ProjectContactRepository

    @Binds
    abstract fun providesProjectPartnerRepository(projectPartnerRepository: ProjectPartnerRepositoryImpl): ProjectPartnerRepository

    companion object {
        fun preferenceWatchedAt(districtState: DistrictState): Preferences.Key<Long> {
            return when (districtState) {
                is DistrictState.DISTRICT -> longPreferencesKey("project_watched_at_district_" + districtState.district.objectId)
                else -> longPreferencesKey("project_watched_at_all")
            }
        }
    }
}
