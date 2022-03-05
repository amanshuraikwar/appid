package io.github.amanshuraikwar.appid.createappgroup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.selectappspackage.SelectAppsPackageView

@Composable
fun CreateAppGroupView(
    modifier: Modifier,
    onCloseClick: () -> Unit
) {
    val vm: CreateAppGroupViewModel = viewModel()

    DisposableEffect(key1 = true) {
        onDispose(vm::cleanup)
    }

    val appList: List<App> by vm.appList.collectAsState()
    val selectApps: Boolean by vm.selectApps.collectAsState()
    val close: Boolean by vm.close.collectAsState()

    LaunchedEffect(key1 = close) {
        if (close) {
            onCloseClick()
        }
    }

    CreateAppGroupView(
        modifier = modifier,
        appList = appList,
        selectApps = selectApps,
        onCloseClick = onCloseClick,
        onCreateAppGroupClick = vm::onCreateAppGroupClick,
        onSelectAppsClick = vm::onSelectAppsClick,
        onAppsSelectedBackClick = vm::onAppsSelectedBackClick,
        onAppsSelected = vm::onAppsSelected,
        onClearAppsClick = vm::onClearAppsClick
    )
}

@Composable
private fun CreateAppGroupView(
    modifier: Modifier = Modifier,
    appList: List<App>,
    selectApps: Boolean,
    onCloseClick: () -> Unit,
    onCreateAppGroupClick: (appGroupName: String) -> Unit,
    onSelectAppsClick: () -> Unit,
    onAppsSelectedBackClick: () -> Unit,
    onAppsSelected: (appList: List<App>) -> Unit,
    onClearAppsClick: () -> Unit
) {
    BackHandler(
        enabled = selectApps,
        onBack = onAppsSelectedBackClick
    )

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = !selectApps,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AppGroupDetailView(
                modifier = modifier,
                appList = appList,
                onCloseClick = onCloseClick,
                onCreateAppGroupClick = onCreateAppGroupClick,
                onSelectAppsClick = onSelectAppsClick,
                onClearAppsClick = onClearAppsClick,
            )
        }

        AnimatedVisibility(
            visible = selectApps,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SelectAppsPackageView(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                onBackClick = onAppsSelectedBackClick,
                onSelected = onAppsSelected
            )
        }
    }
}