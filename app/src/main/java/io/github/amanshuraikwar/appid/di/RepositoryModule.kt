package io.github.amanshuraikwar.appid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.data.AppIdRepositoryImpl
import io.github.amanshuraikwar.appid.db.AppIdDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAppIdRepository(
        @ApplicationContext context: Context,
        dispatcherProvider: CoroutinesDispatcherProvider,
        appIdDb: AppIdDb,
    ): AppIdRepository {
        return AppIdRepositoryImpl(
            applicationContext = context,
            dispatcherProvider = dispatcherProvider,
            appIdDb = appIdDb
        )
    }
}