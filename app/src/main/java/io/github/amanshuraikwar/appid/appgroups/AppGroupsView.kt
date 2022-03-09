package io.github.amanshuraikwar.appid.appgroups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.ui.LoadingView
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun AppGroupsView(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onAppGroupClick: (String) -> Unit
) {
    val vm: AppGroupsViewModel = viewModel()
    val state by vm.state.collectAsState()

    AppGroupsView(
        modifier = modifier,
        state = state,
        paddingValues = paddingValues,
        onAppGroupClick = onAppGroupClick,
        onDeleteAppGroupClick = vm::onDeleteAppGroupClick
    )
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@Composable
internal fun AppGroupsView(
    modifier: Modifier = Modifier,
    state: AppGroupsState,
    paddingValues: PaddingValues,
    onAppGroupClick: (String) -> Unit,
    onDeleteAppGroupClick: (String) -> Unit,
) {
    when (state) {
        AppGroupsState.Fetching -> {
            LoadingView(
                modifier = modifier.fillMaxWidth(),
                text = "Loading app groups..."
            )
        }
        is AppGroupsState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = paddingValues,
            ) {
                items(
                    items = state.appGroupList,
                    key = { app ->
                        app.id
                    },
                ) { item ->
                    AppGroupItemView(
                        appGroup = item,
                        onClick = onAppGroupClick,
                        onDeleteClick = onDeleteAppGroupClick
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
                        .align(Alignment.CenterHorizontally)
                        .padding(32.dp)
                        .size(128.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
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