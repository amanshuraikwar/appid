package io.github.amanshuraikwar.appid.data

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.db.AppIdDb
import io.github.amanshuraikwar.appid.db.asFlow
import io.github.amanshuraikwar.appid.db.mapToList
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class AppIdRepositoryImpl(
    private val applicationContext: Context,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdDb: AppIdDb
) : AppIdRepository {
    private val packageManager = applicationContext.packageManager

    private var isCacheDirty = false
    private val cacheLock = Mutex()

    private val installAppsFlow = MutableSharedFlow<Map<String, App>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private suspend fun fetchInstallApps() {
        withContext(dispatcherProvider.computation) {
            val installedApps = // only get user installed apps
                packageManager
                    .getInstalledApplications(PackageManager.GET_META_DATA)
                    // only get user installed apps
                    .filter { applicationInfo ->
                        (applicationInfo.flags and
                                (ApplicationInfo.FLAG_SYSTEM
                                        or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) == 0
                    }
                    .associate { applicationInfo ->
                        applicationInfo.packageName to
                                App(
                                    name = packageManager.getApplicationLabel(applicationInfo)
                                        .toString(),
                                    packageName = applicationInfo.packageName,
                                    versionName = packageManager.getPackageInfo(
                                        applicationInfo.packageName,
                                        PackageManager.GET_PERMISSIONS
                                    ).versionName ?: "-"
                                )
                    }

            installAppsFlow.tryEmit(installedApps)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    override suspend fun getAllInstalledApps(
        packageName: String
    ): List<App> {
        return withContext(dispatcherProvider.computation) {
            updateInstalledAppCache()
            installAppsFlow.first()
                .filter { (appPackageName, _) ->
                    appPackageName.startsWith(packageName.toLowerCase(Locale.current))
                }
                .map { (_, app) ->
                    app
                }
        }
    }

    override suspend fun getSavedAppGroups(): Flow<List<AppGroup>> {
        fetchInstallApps()

        return appIdDb
            .appGroupEntityQueries
            .findAll()
            .asFlow()
            .mapToList()
            .conflate()
            .combine(installAppsFlow) { appGroupEntityList, appList ->
                appGroupEntityList
                    .mapNotNull { appGroupEntity ->
                        val filteredAppList =
                            appIdDb
                                .appGroupAppListEntityQueries
                                .findAllByGroupId(
                                    groupId = appGroupEntity.id
                                )
                                .executeAsList()
                                .mapNotNull { entity ->
                                    appList.get(entity.packageName)
                                }

                        if (filteredAppList.isEmpty()) {
                            null
                        } else {
                            AppGroup(
                                id = appGroupEntity.id.toString(),
                                name = appGroupEntity.name,
                                apps = filteredAppList
                            )
                        }
                    }
            }
    }

    override suspend fun createAppGroup(
        name: String,
        apps: List<App>
    ): Boolean {
        return withContext(dispatcherProvider.io) {
            suspendCancellableCoroutine { cont ->
                appIdDb.transaction {
                    appIdDb.appGroupEntityQueries.insert(name = name)
                    val groupId = appIdDb.appGroupEntityQueries.lastInsertRowId().executeAsOne()
                    apps.forEach { app ->
                        appIdDb.appGroupAppListEntityQueries.insert(
                            groupId = groupId,
                            packageName = app.packageName
                        )
                    }
                    afterCommit {
                        cont.resumeWith(Result.success(true))
                    }
                    afterRollback {
                        cont.resumeWith(Result.success(false))
                    }
                }
            }
        }
    }

    override suspend fun getAppGroup(id: String): AppGroup? {
        return withContext(dispatcherProvider.io) {
            val allInstalledApps = installAppsFlow.first()

            appIdDb
                .appGroupEntityQueries
                .findById(id = id.toLong())
                .executeAsList()
                .firstOrNull()
                ?.let { appGroupEntity ->
                    val filteredAppList =
                        appIdDb
                            .appGroupAppListEntityQueries
                            .findAllByGroupId(
                                groupId = appGroupEntity.id
                            )
                            .executeAsList()
                            .mapNotNull { entity ->
                                allInstalledApps[entity.packageName]
                            }

                    AppGroup(
                        id = appGroupEntity.id.toString(),
                        name = appGroupEntity.name,
                        apps = filteredAppList
                    )
                }
        }
    }

    override suspend fun deleteAppGroup(id: String): Boolean {
        return withContext(dispatcherProvider.io) {
            suspendCancellableCoroutine { cont ->
                appIdDb.transaction {
                    appIdDb.appGroupEntityQueries.deleteById(id = id.toLong())
                    appIdDb.appGroupAppListEntityQueries.deleteAllByGroupId(
                        groupId = id.toLong()
                    )
                    afterCommit {
                        cont.resumeWith(Result.success(true))
                    }
                    afterRollback {
                        cont.resumeWith(Result.success(false))
                    }
                }
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

    override suspend fun appWasUninstalled(packageName: String) {
        cacheLock.withLock {
            isCacheDirty = true
        }
    }

    override suspend fun updateInstalledAppCache() {
        cacheLock.withLock {
            if (isCacheDirty) {
                fetchInstallApps()
                isCacheDirty = false
            }
        }
    }
}