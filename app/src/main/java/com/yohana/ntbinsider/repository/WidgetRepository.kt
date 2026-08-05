package com.yohana.ntbinsider.repository

import com.yohana.ntbinsider.data.ObjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WidgetRepository {
    @Provides
    @ViewModelScoped
    fun provideRepository(objectDao: ObjectDao) = ObjectRepository(objectDao)
}