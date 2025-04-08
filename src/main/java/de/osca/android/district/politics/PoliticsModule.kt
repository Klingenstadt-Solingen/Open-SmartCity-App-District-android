package de.osca.android.district.politics

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.DistrictModule
import de.osca.android.district.core.plugin.Plugin
import de.osca.android.district.politics.data.repository.AgendaRepository
import de.osca.android.district.politics.data.repository.MeetingRepository
import de.osca.android.district.politics.data.repository.MembershipRepository
import de.osca.android.district.politics.data.repository.OrganizationRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private val json = Json { ignoreUnknownKeys = true }

fun retrofitbuilder(oscaHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(DistrictModule.politicsURL ?: "")
        .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .client(oscaHttpClient)
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PoliticsModule {
    @IntoSet
    @Binds
    abstract fun providesPolitcsPlugin(plugin: PoliticsPlugin): Plugin
}

@Module
@InstallIn(SingletonComponent::class)
class PoliticsModuleNonAbstract {
    @Singleton
    @Provides
    fun organizationRepository(oscaHttpClient: OkHttpClient): OrganizationRepository =
        retrofitbuilder(oscaHttpClient)
            .create(OrganizationRepository::class.java)

    @Singleton
    @Provides
    fun membershipRepository(oscaHttpClient: OkHttpClient): MembershipRepository =
        retrofitbuilder(oscaHttpClient)
            .create(MembershipRepository::class.java)

    @Singleton
    @Provides
    fun meetingRepository(oscaHttpClient: OkHttpClient): MeetingRepository =
        retrofitbuilder(oscaHttpClient)
            .create(MeetingRepository::class.java)

    @Singleton
    @Provides
    fun agendaRepository(oscaHttpClient: OkHttpClient): AgendaRepository =
        retrofitbuilder(oscaHttpClient)
            .create(AgendaRepository::class.java)
}
