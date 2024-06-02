package com.koniukhov.brushbliss.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherMain

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @DispatcherMain
    fun provideDispatcherMain(): CoroutineDispatcher{
        return Dispatchers.Main
    }
}