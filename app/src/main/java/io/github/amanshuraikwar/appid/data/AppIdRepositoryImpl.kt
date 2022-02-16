package io.github.amanshuraikwar.appid.data

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.db.AppIdDb
import io.github.amanshuraikwar.appid.db.asFlow
import io.github.amanshuraikwar.appid.db.mapToList
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppIdRepositoryImpl(
    private val applicationContext: Context,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdDb: AppIdDb
) : AppIdRepository {
    private val packageManager = applicationContext.packageManager

    private val allInstalledApps: List<App> by lazy {
        packageManager
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .map { applicationInfo ->
                App(
                    name = packageManager.getApplicationLabel(applicationInfo).toString(),
                    packageName = applicationInfo.packageName,
                    versionName = packageManager.getPackageInfo(
                        applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS
                    ).versionName ?: "-"
                )
            }
    }

    @SuppressLint("QueryPermissionsNeeded")
    override suspend fun getAllInstalledApps(
        packageName: String
    ): List<App> {
        return withContext(dispatcherProvider.computation) {
            allInstalledApps.filter { app ->
                app.packageName.startsWith(packageName.toLowerCase(Locale.current))
            }
        }
    }

    override suspend fun getSavedAppGroups(): Flow<List<AppGroup>> {
        return appIdDb
            .appGroupEntityQueries
            .findAll()
            .asFlow()
            .mapToList()
            .conflate()
            .map {
                it.map { appGroupEntity ->
                    AppGroup(
                        id = appGroupEntity.id.toString(),
                        name = appGroupEntity.packageName,
                        apps = allInstalledApps.filter { app ->
                            app.packageName.startsWith(
                                appGroupEntity.packageName.toLowerCase(Locale.current)
                            )
                        }
                    )
                }
            }
    }

    override suspend fun createAppGroup(packageName: String) {
        withContext(dispatcherProvider.io) {
            appIdDb
                .appGroupEntityQueries
                .insert(packageName = packageName)
        }
    }

    override suspend fun getAppGroup(id: String): AppGroup? {
        return withContext(dispatcherProvider.io) {
            appIdDb
                .appGroupEntityQueries
                .findById(id = id.toLong())
                .executeAsList()
                .firstOrNull()
                ?.let { appGroupEntity ->
                    AppGroup(
                        id = appGroupEntity.id.toString(),
                        name = appGroupEntity.packageName,
                        apps = allInstalledApps.filter { app ->
                            app.packageName.startsWith(
                                appGroupEntity.packageName.toLowerCase(Locale.current)
                            )
                        }
                    )
                }
        }
    }

    override suspend fun deleteApp(packageName: String) {
        withContext(dispatcherProvider.io) {
            packageManager.packageInstaller.uninstall(
                packageName,
                PendingIntent.getBroadcast(
                    applicationContext,
                    1001,
                    Intent("android.intent.action.MAIN"),
                    PendingIntent.FLAG_IMMUTABLE
                ).intentSender
            )
        }
    }
}