package de.osca.android.district.mobility

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.mobility.data.MobilityParseObjectRegistration
import de.osca.android.district.mobility.data.repository.MobilityRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

//val Context.eventDataStore: DataStore<Preferences> by preferencesDataStore(name = "event_preferences")

var baseUrl = "http://192.168.178.183:8080/api/v1/"

private val json = Json { ignoreUnknownKeys = true }


@Module
@InstallIn(SingletonComponent::class)
class MobilityModuleNonAbstract {

    @Singleton
    @Provides
    @Named("Mobility")
    fun provideMobilityRetrofit(oscaHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .client(oscaHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun mobilityRepository(@Named("Mobility") retrofit: Retrofit): MobilityRepository = retrofit.create(
        MobilityRepository::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MobilityModule {
    @IntoSet
    @Binds
    abstract fun plugin(plugin: MobilityPlugin): Plugin

    @Binds
    @IntoSet
    abstract fun mobilityParseObjectRegistration(parseObjectRegistration: MobilityParseObjectRegistration): ParseObjectRegistration
}
