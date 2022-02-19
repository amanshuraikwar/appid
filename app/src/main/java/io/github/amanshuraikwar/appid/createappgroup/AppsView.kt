package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AppsOutage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.ui.HeaderView
import io.github.amanshuraikwar.appid.ui.LoadingView
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun AppsView(
    modifier: Modifier = Modifier,
    state: CreateAppGroupState,
) {
    Column(modifier) {
        when (state) {
            CreateAppGroupState.Loading -> {
                LoadingView(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Loading apps..."
                )
            }
            is CreateAppGroupState.Success -> {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    LazyColumn {
                        item {
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
                }
            }
            is CreateAppGroupState.NoApps -> {
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