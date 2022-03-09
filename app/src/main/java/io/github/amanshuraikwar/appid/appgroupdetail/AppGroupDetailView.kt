package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.ui.ErrorView
import io.github.amanshuraikwar.appid.ui.HeaderView
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.UiError
import io.github.amanshuraikwar.appid.ui.collectAsUiErrorState
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.outline
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
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Box(
            Modifier.fillMaxSize()
        ) {
            var actionBarHeight by remember { mutableStateOf(0) }

            if (state is AppGroupDetailState.Success) {
                var bottomBarHeight by remember { mutableStateOf(0) }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = with(LocalDensity.current) { bottomBarHeight.toDp() }
                        )
                        .padding(
                            top = with(LocalDensity.current) { actionBarHeight.toDp() }
                        ),
                    color = MaterialTheme.colors.surface
                ) {
                    Column {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                                .padding(end = 16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            HeaderView(
                                Modifier.fillMaxWidth(),
                                title = "Installed Apps"
                            )

                            androidx.compose.animation.AnimatedVisibility(
                                visible = state is AppGroupDetailState.Success.Idle,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                if (state is AppGroupDetailState.Success.Idle) {
                                    val offsetX: Dp by animateDpAsState(
                                        targetValue = when (state.appDisplayType) {
                                            AppGroupDetailState.AppDisplayType.GRID -> 0.dp
                                            AppGroupDetailState.AppDisplayType.LIST -> 36.dp
                                        }
                                    )

                                    Box(
                                        modifier = Modifier
                                            .padding(start = offsetX)
                                            .clip(shape = RoundedCornerShape(100))
                                            .background(MaterialTheme.colors.primary)
                                            .size(36.dp)
                                    )

                                    Row(
                                        Modifier
                                            .clip(RoundedCornerShape(50))
                                            .background(MaterialTheme.colors.outline)
                                    ) {
                                        val gridIconTint: Color by animateColorAsState(
                                            targetValue = when (state.appDisplayType) {
                                                AppGroupDetailState.AppDisplayType.GRID -> {
                                                    MaterialTheme.colors.onPrimary
                                                }
                                                AppGroupDetailState.AppDisplayType.LIST -> {
                                                    MaterialTheme.colors.onSurface.disabled
                                                }
                                            }
                                        )

                                        val listIconTint: Color by animateColorAsState(
                                            targetValue = when (state.appDisplayType) {
                                                AppGroupDetailState.AppDisplayType.GRID -> {
                                                    MaterialTheme.colors.onSurface.disabled
                                                }
                                                AppGroupDetailState.AppDisplayType.LIST -> {
                                                    MaterialTheme.colors.onPrimary
                                                }
                                            }
                                        )

                                        Icon(
                                            imageVector = Icons.Rounded.GridView,
                                            contentDescription = "Grid View",
                                            tint = gridIconTint,
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(100))
                                                .clickable(onClick = onGridViewClick)
                                                .padding(8.dp)
                                                .size(20.dp)
                                        )

                                        Icon(
                                            imageVector = Icons.Rounded.ViewList,
                                            contentDescription = "List View",
                                            tint = listIconTint,
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(100))
                                                .clickable(onClick = onListViewClick)
                                                .padding(8.dp)
                                                .size(20.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Divider()

                        val appLauncher = rememberAppLauncher()

                        when (state.appDisplayType) {
                            AppGroupDetailState.AppDisplayType.GRID -> {
                                AppGroupView(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    appGroup = state.appGroup,
                                    onAppClick = appLauncher::launch
                                )
                            }
                            AppGroupDetailState.AppDisplayType.LIST -> {
                                LazyColumn {
                                    items(
                                        items = state.appGroup.apps,
                                        key = { it.packageName }
                                    ) { item ->
                                        AppView(
                                            app = item,
                                            onClick = appLauncher::launch,
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

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .animateContentSize()
                        .onSizeChanged {
                            bottomBarHeight = it.height
                        },
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
            }

            ActionBarView(
                modifier = Modifier.onSizeChanged {
                    actionBarHeight = it.height
                }
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            onClick = onBackClick
                        )

                        if (state is AppGroupDetailState.Success) {
                            IconButton(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete",
                                onClick = {
                                    onDeleteAppGroupClick(state.appGroup)
                                },
                                foregroundColor = MaterialTheme.colors.error
                            )
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = when (state) {
                            is AppGroupDetailState.AppGroupNotFound -> "App group not found"
                            AppGroupDetailState.Loading -> ""
                            is AppGroupDetailState.Success -> state.appGroup.name
                        },
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }

            ErrorView(
                modifier = Modifier
                    .statusBarsPadding(),
                error = error,
                enterFromBottom = false
            )
        }
    }
}