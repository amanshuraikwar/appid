package io.github.amanshuraikwar.appid.data

import io.github.amanshuraikwar.appid.model.App

interface AppIdRepository {
    suspend fun getAllInstalledApps(packageName: String): List<App>
}