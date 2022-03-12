package io.github.amanshuraikwar.appid.appgroups

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Outlet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.ui.ProgressView
import io.github.amanshuraikwar.appid.ui.EmptyStateView

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

@OptIn(ExperimentalFoundationApi::class)
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
            Box(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProgressView()
            }
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
                        modifier = Modifier.animateItemPlacement(),
                        appGroup = item,
                        onClick = onAppGroupClick,
                        onDeleteClick = onDeleteAppGroupClick
                    )
                }
            }
        }
        AppGroupsState.Empty -> {
            EmptyStateView(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                imageVector = Icons.Rounded.Outlet,
                contentDescription = "No App Groups",
                title = "Nothing to see here",
                description = "Create an app group and it will show up here",
            )
        }
    }
}