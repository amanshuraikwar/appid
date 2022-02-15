package io.github.amanshuraikwar.appid.appgroups

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.HeaderView
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun AppGroupsView(
    modifier: Modifier = Modifier,
) {
    val vm: AppGroupsViewModel = viewModel()
    val state by vm.state.collectAsState()

    AppGroupsView(
        modifier = modifier,
        state = state
    )
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AppGroupsView(
    modifier: Modifier = Modifier,
    state: AppGroupsState
) {
    when (state) {
        AppGroupsState.Fetching -> {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading groups...",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        is AppGroupsState.Success -> {
            LazyColumn(
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars,
                    applyTop = false,
                    applyBottom = true,
                    additionalBottom = 128.dp
                ),
            ) {
                stickyHeader {
                    HeaderView(title = "App Groups")
                }

                items(
                    items = state.appGroupList,
                    key = { app ->
                        app.id
                    },
                ) { item ->
                    AppGroupView(
                        appGroup = item
                    )
                }
            }
        }
        AppGroupsState.Empty -> {
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Apps,
                    contentDescription = "Search",
                    tint = MaterialTheme.colors.primary.disabled,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(32.dp)
                        .size(128.dp)
                )

                Text(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(16.dp),
                    text = "You have not created aby app groups yet.",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary.medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}