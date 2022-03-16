package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.acmNavigationBarsPadding
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.ErrorView
import io.github.amanshuraikwar.appid.ui.UiError
import io.github.amanshuraikwar.appid.ui.collectAsUiErrorState
import io.github.amanshuraikwar.appid.util.rememberAppUninstaller

@Composable
fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    id: String,
    onBackClick: () -> Unit
) {
    val vm: AppGroupDetailViewModel = viewModel()
    val state by vm.state.collectAsState()
    val error by vm.error.collectAsUiErrorState()

    LaunchedEffect(
        key1 = id
    ) {
        vm.init(id)
    }

    val backClick by vm.backClick.collectAsState(false)
    LaunchedEffect(key1 = backClick) {
        if (backClick) {
            onBackClick()
        }
    }

    val appUninstaller = rememberAppUninstaller()

    AppGroupDetailView(
        modifier = modifier,
        state = state,
        error = error,
        onDeleteAllAppsClick = { appGroup ->
            vm.onDeleteClick(
                appGroup = appGroup,
                appUninstaller = appUninstaller
            )
        },
        onBackClick = onBackClick,
        onGridViewClick = vm::onGridViewClick,
        onListViewClick = vm::onListViewClick,
        onAppDeleteClick = { appGroup, app ->
            vm.onDeleteClick(
                appGroup = appGroup,
                app = app,
                appUninstaller = appUninstaller
            )
        },
        onDeleteAppGroupClick = vm::onDeleteAppGroupClick
    )
}

@Composable
internal fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    state: AppGroupDetailState,
    error: UiError?,
    onDeleteAllAppsClick: (AppGroup) -> Unit,
    onBackClick: () -> Unit,
    onGridViewClick: () -> Unit,
    onListViewClick: () -> Unit,
    onAppDeleteClick: (AppGroup, App) -> Unit,
    onDeleteAppGroupClick: (AppGroup) -> Unit,
) {
    Surface(modifier, elevation = 2.dp) {
        AppIdScaffold(
            modifier = Modifier
                .fillMaxSize(),
            actionBar = {
                ActionBar(
                    state = state,
                    onBackClick = onBackClick,
                    onGridViewClick = onGridViewClick,
                    onListViewClick = onListViewClick,
                    onDeleteAppGroupClick = onDeleteAppGroupClick
                )
            },
            bottomBar = {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.surface)
                        .animateContentSize()
                ) {
                    if (state is AppGroupDetailState.Success) {
                        Divider()

                        BottomBarView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .acmNavigationBarsPadding(),
                            state = state,
                            onDeleteClick = {
                                onDeleteAllAppsClick(state.appGroup)
                            }
                        )
                    }
                }
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (state is AppGroupDetailState.Success) {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        AppsView(
                            state = state,
                            onAppDeleteClick = onAppDeleteClick
                        )
                    }
                }

                ErrorView(
                    modifier = Modifier,
                    error = error,
                    enterFromBottom = true
                )
            }
        }
    }
}