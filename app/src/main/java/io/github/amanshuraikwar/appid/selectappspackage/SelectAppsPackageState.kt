package io.github.amanshuraikwar.appid.selectappspackage

import io.github.amanshuraikwar.appid.model.App

internal sealed class SelectAppsPackageState {
    object Idle : SelectAppsPackageState()

    data class NoApps(
        val packageName: String,
    ) : SelectAppsPackageState()

    data class Success(
        val packageName: String,
        val apps: List<App>
    ) : SelectAppsPackageState()
}