package io.github.amanshuraikwar.appid.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AppsOutage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun AppsView(
    modifier: Modifier = Modifier,
    state: HomeViewState
) {
    when (state) {
        HomeViewState.Loading -> {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
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
        }
        is HomeViewState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(
                    bottom =
                    rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars,
                        applyTop = false,
                        applyBottom = true,
                    ).calculateBottomPadding()
                            +
                            rememberInsetsPaddingValues(
                                insets = LocalWindowInsets.current.ime,
                                applyTop = false,
                                applyBottom = true,
                            ).calculateBottomPadding() + 72.dp
                )
            ) {
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
        HomeViewState.NoApps -> {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
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