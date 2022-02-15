package io.github.amanshuraikwar.appid.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.appgroups.AppGroupsView
import io.github.amanshuraikwar.appid.ui.SearchBar
import io.github.amanshuraikwar.appid.ui.theme.appName

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
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Surface(
                    color = MaterialTheme.colors.surface,
                    elevation = 8.dp
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(16.dp),
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.appName,
                    )
                }

                AppGroupsView(
                    modifier = Modifier,
                )
            }

            var visible by remember {
                mutableStateOf(false)
            }

            BackHandler(enabled = visible) {
                visible = false
            }

            FloatingActionButton(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { visible = true }
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

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    it
                },
                exit = slideOutVertically {
                    it
                }
            ) {
                AppsView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    onCreateGroupClick = { packageName ->
                        visible  = false
                    },
                    onBackClick = {
                        visible = false
                    }
                )
            }
        }
    }
}



