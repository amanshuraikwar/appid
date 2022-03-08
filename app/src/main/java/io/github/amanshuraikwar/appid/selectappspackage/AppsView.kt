package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.ui.theme.medium

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun AppsView(
    modifier: Modifier = Modifier,
    state: SelectAppsPackageState,
) {
    Column(modifier) {
        when (state) {
            is SelectAppsPackageState.Success -> {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    LazyColumn {
//                        item {
//                            HeaderView(title = "Installed Apps")
//                        }

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
            is SelectAppsPackageState.NoApps -> {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "No matching apps found.",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground.medium
                )
            }
            SelectAppsPackageState.Idle -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Search apps",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onBackground.medium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}