package io.github.amanshuraikwar.appid.data

import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import kotlinx.coroutines.flow.Flow

interface AppIdRepository {
    suspend fun getAllInstalledApps(packageName: String): List<App>
    suspend fun getSavedAppGroups(): Flow<List<AppGroup>>
    suspend fun createAppGroup(packageName: String)
    suspend fun getAppGroup(id: String): AppGroup?
    suspend fun deleteApp(packageName: String)
    suspend fun appWasUninstalled(packageName: String)
    suspend fun updateInstalledAppCache()
}