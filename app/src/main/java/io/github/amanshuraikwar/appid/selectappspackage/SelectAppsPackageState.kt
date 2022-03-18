package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.github.amanshuraikwar.appid.model.App

internal sealed class SelectAppsPackageState {
    object Idle : SelectAppsPackageState()

    data class NoApps(
        val packageName: String,
    ) : SelectAppsPackageState()

    data class Success(
        var selectedAppCount: MutableState<Int>,
        val packageName: String,
        val apps: SnapshotStateList<SelectableApp>
    ) : SelectAppsPackageState()
}

internal data class SelectableApp(
    val selected: Boolean,
    val app: App
)