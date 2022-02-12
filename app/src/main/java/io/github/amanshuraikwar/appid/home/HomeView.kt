package io.github.amanshuraikwar.appid.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.ui.SearchBar

@Composable
fun HomeView(
    viewModel: HomeViewModel = viewModel()
) {
    val state: HomeViewState by viewModel.state.collectAsState()
    val search: String by viewModel.searchFlow.collectAsState("")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AppsView(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 56.dp),
                    state = state
                )

                SearchBar(
                    modifier = Modifier,
                    onSearch = viewModel::onSearch
                )
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = state is HomeViewState.Success
                        && (state as HomeViewState.Success).apps.size <= 16
                        && search.isNotEmpty(),
                enter = slideInVertically {
                    it
                },
                exit = slideOutVertically {
                    it
                }
            ) {
                val navigationBarPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars,
                    applyTop = false,
                    applyBottom = true,
                ).calculateBottomPadding()

                val imePadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.ime,
                    applyTop = false,
                    applyBottom = true,
                ).calculateBottomPadding()

                val bottomPadding: Dp by animateDpAsState(
                    targetValue = if (imePadding == 0.dp) {
                        navigationBarPadding + 16.dp
                    } else {
                        imePadding + 16.dp
                    }
                )

                FloatingActionButton(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .padding(bottom = bottomPadding),
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = { /*TODO*/ }
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clip(shape = MaterialTheme.shapes.small)
                                .padding(vertical = 16.dp)
                                .padding(start = 16.dp)
                                .size(24.dp)
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(vertical = 16.dp)
                                .padding(start = 12.dp, end = 16.dp),
                            text = "Create App Group",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}



