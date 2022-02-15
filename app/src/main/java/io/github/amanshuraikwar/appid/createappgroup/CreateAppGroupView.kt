package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.SearchBar

@Composable
fun CreateAppGroupView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val vm: CreateAppGroupViewModel = viewModel()
    val state by vm.state.collectAsState()

    CreateAppGroupView(
        modifier = modifier,
        state = state,
        onBackClick = onBackClick,
        onSearch = vm::onSearch,
        onCreateGroupClick = {
            vm.onCreateGroupClick(it)
            onBackClick()
        }
    )
}

@Composable
internal fun CreateAppGroupView(
    modifier: Modifier = Modifier,
    state: CreateAppGroupState,
    onBackClick: () -> Unit,
    onSearch: (query: String) -> Unit,
    onCreateGroupClick: (String) -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        var searchBarHeight by remember { mutableStateOf(0) }
        var bottomBarHeight by remember { mutableStateOf(0) }

        AppsView(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = with(LocalDensity.current) { searchBarHeight.toDp() },
                    bottom = with(LocalDensity.current) { bottomBarHeight.toDp() },
                ),
            state = state,
        )

        SearchBar(
            modifier = Modifier.onSizeChanged {
                searchBarHeight = it.height
            },
            onSearch = onSearch,
            onBackClick = onBackClick
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .onSizeChanged {
                    bottomBarHeight = it.height
                },
            elevation = 2.dp
        ) {
            BottomBarView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(rememberImeAndNavBarInsetsPaddingValues()),
                state = state,
                onCreateGroupClick = onCreateGroupClick
            )
        }
    }
}

