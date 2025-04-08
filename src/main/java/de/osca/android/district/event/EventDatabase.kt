package de.osca.android.district.event

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.osca.android.district.event.data.boundaries.EventDao
import de.osca.android.district.event.data.model.EventEntity
import javax.inject.Singleton

@Database(entities = [EventEntity::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}


@Module
@InstallIn(SingletonComponent::class)
class EventDatabaseModule {
    @Singleton
    @Provides
    fun provideEventDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(context, EventDatabase::class.java, "EventDatabase").allowMainThreadQueries().build()
    }
}