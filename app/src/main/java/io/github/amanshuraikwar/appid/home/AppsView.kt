package io.github.amanshuraikwar.appid.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AppsOutage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.SearchBar
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun AppsView(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = viewModel(),
    onCreateGroupClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val state: HomeViewState by vm.state.collectAsState()

    AppsView(
        modifier = modifier,
        state = state,
        vm::onSearch,
        onCreateGroupClick = {
            vm.onCreateGroupClick(it)
            onCreateGroupClick(it)
        },
        onBackClick
    )
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun AppsView(
    modifier: Modifier = Modifier,
    state: HomeViewState,
    onSearch: (String) -> Unit,
    onCreateGroupClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        SearchBar(onSearch = onSearch, onBackClick = onBackClick)

        when (state) {
            HomeViewState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading apps...",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
            }
            is HomeViewState.Success -> {
                var x by remember { mutableStateOf(0) }

                Box(
                    Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        contentPadding = rememberImeAndNavBarInsetsPaddingValues(x)
                    ) {
                        stickyHeader {
                            HeaderView(title = "Installed Apps")
                        }

                        items(
                            items = state.apps,
                            key = { app ->
                                app.packageName
                            },
                        ) { item ->
                            AppView(
                                modifier = Modifier.animateItemPlacement(),
                                app = item
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        elevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(rememberImeAndNavBarInsetsPaddingValues())
                                .onSizeChanged {
                                    x = it.height
                                },
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (state.apps.size > 20) {
                                Text(
                                    modifier = Modifier
                                        .background(MaterialTheme.colors.error)
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    text = "We cannot create a group of more than 20 apps",
                                    color = MaterialTheme.colors.onError,
                                    style = MaterialTheme.typography.body1
                                )
                            } else {
                                Surface(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 12.dp
                                        )
                                        .clickable {
                                            onCreateGroupClick(state.packageName)
                                        },
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 16.dp,
                                                vertical = 12.dp
                                            ),
                                        text = "Create Group",
                                        style = MaterialTheme.typography.button
                                    )
                                }
                            }
                        }
                    }
                }
            }
            HomeViewState.NoApps -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "No matching apps!",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground.medium
                )

                Icon(
                    imageVector = Icons.Rounded.AppsOutage,
                    contentDescription = "Search",
                    tint = MaterialTheme.colors.onBackground.disabled,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(128.dp)
                )
            }
        }
    }
}

@Composable
private fun rememberImeAndNavBarInsetsPaddingValues(
    extraPx: Int = 0
): PaddingValues {
    return PaddingValues(
        bottom =
        max(
            rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false,
                applyBottom = true,
            ).calculateBottomPadding(),
            rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.ime,
                applyTop = false,
                applyBottom = true,
            ).calculateBottomPadding()
        ) + with(LocalDensity.current) { extraPx.toDp() }
    )
}