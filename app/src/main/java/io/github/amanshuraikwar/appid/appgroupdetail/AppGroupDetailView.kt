package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.ui.ErrorView
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.UiError
import io.github.amanshuraikwar.appid.ui.collectAsUiErrorState
import io.github.amanshuraikwar.appid.ui.rememberAppIdIndication
import io.github.amanshuraikwar.appid.util.rememberAppLauncher
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
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface,
        elevation = 2.dp
    ) {
        AppIdScaffold(
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
                if (state is AppGroupDetailState.Success) {
                    Surface(
                        modifier = Modifier
                            //.align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .animateContentSize(),
                        elevation = 4.dp
                    ) {
                        Column {
                            Divider()

                            BottomBarView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(rememberImeAndNavBarInsetsPaddingValues()),
                                state = state,
                                onDeleteClick = {
                                    onDeleteAllAppsClick(state.appGroup)
                                }
                            )
                        }
                    }
                } else {
                    Box {}
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
                        val appLauncher = rememberAppLauncher()

                        when (state.appDisplayType) {
                            AppGroupDetailState.AppDisplayType.GRID -> {
                                AppGroupView(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                        .padding(start = 16.dp, end = 16.dp),
                                    appGroup = state.appGroup,
                                    onAppClick = appLauncher::launch,
                                    appIconSize = 48.dp
                                )
                            }
                            AppGroupDetailState.AppDisplayType.LIST -> {
                                LazyColumn(
                                    Modifier
                                        .fillMaxWidth(),
                                ) {
                                    items(
                                        items = state.appGroup.apps,
                                        key = { it.packageName }
                                    ) { item ->
                                        AppView(
                                            app = item,
                                            onClick = appLauncher::launch,
                                            appIconSize = 40.dp,
                                            onDeleteClick = {
                                                onAppDeleteClick(state.appGroup, it)
                                            }
                                        )
                                    }
                                }
                            }
                        }
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