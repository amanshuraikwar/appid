package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.ErrorView
import io.github.amanshuraikwar.appid.ui.UiError
import kotlinx.coroutines.delay

@Composable
fun SelectAppsPackageView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSelected: (appList: List<App>) -> Unit,
) {
    val vm: SelectAppsPackageViewModel = viewModel()
    val state by vm.state.collectAsState()
    val error by produceState<UiError?>(null, vm.error) {
        vm.error.collect {
            value = it
            if (it != null) {
                delay(3000)
            }
        }
    }

    val selectApps by vm.selectAppsFlow.collectAsState()
    DisposableEffect(selectApps) {
        selectApps?.let(onSelected)
        onDispose(vm::onDispose)
    }

    SelectAppsPackageView(
        modifier = modifier,
        state = state,
        error = error,
        onBackClick = onBackClick,
        onSearch = vm::onSearch,
        onSelectClick = vm::onSelectClick,
        onAppClick = vm::onAppClick,
        onSelectAllClick = vm::onSelectAllClick,
        onDeselectAllClick = vm::onDeselectAllClick,
    )
}

@Composable
internal fun SelectAppsPackageView(
    modifier: Modifier = Modifier,
    state: SelectAppsPackageState,
    error: UiError?,
    onBackClick: () -> Unit,
    onSearch: (query: String) -> Unit,
    onSelectClick: () -> Unit,
    onAppClick: (SelectableApp) -> Unit,
    onSelectAllClick: () -> Unit,
    onDeselectAllClick: () -> Unit,
) {
    AppIdScaffold(
        modifier = modifier,
        actionBar = {
            ActionBar(
                state = state,
                onBackClick = onBackClick,
                onSearch = onSearch,
                onSelectAllClick = onSelectAllClick,
                onDeselectAllClick = onDeselectAllClick,
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colors.surface
            ) {
                BottomBarView(
                    modifier = Modifier
                        .padding(rememberImeAndNavBarInsetsPaddingValues()),
                    state = state,
                    onSelectClick = onSelectClick
                )
            }
        }
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AppsView(
                Modifier
                    .clickable(enabled = false) { }
                    .fillMaxSize(),
                state = state,
                onAppClick = onAppClick
            )

            ErrorView(
                error = error,
                enterFromBottom = true
            )
        }
    }
}

