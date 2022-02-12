package io.github.amanshuraikwar.appid.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import io.github.amanshuraikwar.appid.model.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppIdRepositoryImpl(
    applicationContext: Context
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
        return withContext(Dispatchers.Default) {
            allInstalledApps.filter { app ->
                app.packageName.startsWith(packageName.toLowerCase(Locale.current))
            }
        }
    }
}