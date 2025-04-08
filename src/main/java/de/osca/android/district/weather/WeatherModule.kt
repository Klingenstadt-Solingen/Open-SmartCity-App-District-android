package de.osca.android.district.weather

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.weather.data.WeatherParseObjectRegistration
import de.osca.android.district.weather.data.boundaries.WeatherRepository
import de.osca.android.district.weather.data.repository.WeatherRepositoryImpl
import javax.inject.Singleton

val Context.weatherDataStore: DataStore<Preferences> by preferencesDataStore(name = WeatherModule.WEATHER_PREF_NAME)

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: WeatherPlugin): Plugin

    companion object {
        const val WEATHER_PREF_NAME = "weather"
        val SELECTED_WEATHER_ID = stringPreferencesKey("selected_weather_id")
    }
}

@Module
@InstallIn(SingletonComponent::class)
class WeatherNoAbstractModule {
    @Singleton
    @Provides
    fun providesWeatherRepository(): WeatherRepository = WeatherRepositoryImpl()

    @Singleton
    @Provides
    @IntoSet
    fun weatherParseObjectRegistration(): ParseObjectRegistration = WeatherParseObjectRegistration()
}
