package de.osca.android.district.dashboard

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.plugin.Plugin

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: DashboardPlugin): Plugin
}
