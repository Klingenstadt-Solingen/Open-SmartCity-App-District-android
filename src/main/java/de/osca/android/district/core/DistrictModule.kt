package de.osca.android.district.core

import androidx.compose.ui.graphics.Color
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import de.osca.android.district.core.data.DistrictParseObjectRegistration
import de.osca.android.district.core.data.boundaries.DistrictRepository
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.core.data.repository.DistrictRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object DistrictModule {
    @Singleton
    @Provides
    @IntoSet
    fun providesDistrictParseObjectRegistration(): ParseObjectRegistration = DistrictParseObjectRegistration()

    @Singleton
    @Provides
    fun providesDistrictRepository(): DistrictRepository = DistrictRepositoryImpl()

    var politicsURL: String? = null
}

const val PAGINATION_STEP = 20
const val CACHE_TIME: Long = 600000 // milliseconds
val PRIMARY_COLOR: Color = Color(0f, 0.263f, 0.451f)
val SECONDARY_COLOR: Color = Color(1f, 0.74f, 0f)
val DISABLED_COLOR: Color = Color(0.949f, 0.949f, 0.949f)
const val DISTRICT_DATABASE_NAME = "district-db"
const val NEARBY_MIN_DISTANCE: Float = 1f
const val NEARBY_MAX_DISTANCE: Float = 100f
