package io.github.amanshuraikwar.appid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.data.AppIdRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideAppIdRepository(
        @ApplicationContext context: Context,
    ): AppIdRepository {
        return AppIdRepositoryImpl(applicationContext = context)
    }
}