package io.github.amanshuraikwar.appid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Singleton
    @Provides
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
        )
    }
}