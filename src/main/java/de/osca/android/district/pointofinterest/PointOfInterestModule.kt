package de.osca.android.district.pointofinterest

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.pointofinterest.data.PointOfInterestObjectRegistration
import de.osca.android.district.pointofinterest.data.boundary.PoiCategoryRepository
import de.osca.android.district.pointofinterest.data.boundary.PoiRepository
import de.osca.android.district.pointofinterest.data.repository.PoiCategoryRepositoryImpl
import de.osca.android.district.pointofinterest.data.repository.PoiRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class PointOfInterestModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: PointOfInterestPlugin): Plugin

    @Binds
    abstract fun bindsPoiRepository(repository: PoiRepositoryImpl): PoiRepository

    @Binds
    abstract fun bindsPoiCategoryRepository(repository: PoiCategoryRepositoryImpl): PoiCategoryRepository

    @Binds
    @IntoSet
    abstract fun bindsPoiParseObjectRegistration(registration: PointOfInterestObjectRegistration): ParseObjectRegistration
}
