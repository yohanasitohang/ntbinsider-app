package com.yohana.ntbinsider.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDepedency {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, ObjectDB::class.java, "ntbinsider.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: ObjectDB) = database.objectDao()
}