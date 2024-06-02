package com.koniukhov.brushbliss.di

import android.content.Context
import com.koniukhov.brushbliss.data.UserSettingsManager
import com.koniukhov.brushbliss.data.UserSettingsManager.Companion.datastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideUserSettingsManager(@ApplicationContext context: Context): UserSettingsManager{
        return UserSettingsManager(context.datastore)
    }
}