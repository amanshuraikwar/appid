package io.github.amanshuraikwar.appid.createappgroup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.selectappspackage.SelectAppsPackageView
import io.github.amanshuraikwar.appid.ui.UiError
import io.github.amanshuraikwar.appid.ui.collectAsUiErrorState

@Composable
fun CreateAppGroupView(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit
) {
    val vm: CreateAppGroupViewModel = viewModel()

    DisposableEffect(key1 = true) {
        onDispose(vm::cleanup)
    }

    val appList: List<App> by vm.appList.collectAsState()
    val selectApps: Boolean by vm.selectApps.collectAsState()
    val close by vm.close.collectAsState()
    val error by vm.error.collectAsUiErrorState()

    LaunchedEffect(key1 = close) {
        if (close) {
            onCloseClick()
        }
    }

    CreateAppGroupView(
        modifier = modifier,
        appList = appList,
        error = error,
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
    error: UiError?,
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

    var appGroupName by rememberSaveable {
        mutableStateOf("")
    }

    DisposableEffect(key1 = true) {
        onDispose {
            appGroupName = ""
        }
    }

    Surface(modifier, elevation = 2.dp) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = !selectApps,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppGroupDetailView(
                    appGroupName = appGroupName,
                    onAppGroupNameValueChange = {
                        appGroupName = it
                    },
                    appList = appList,
                    error = error,
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
}