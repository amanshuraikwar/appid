package io.github.amanshuraikwar.appid.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.navigationBarsPadding
import io.github.amanshuraikwar.appid.createappgroup.CreateAppGroupView
import io.github.amanshuraikwar.appid.appgroups.AppGroupsView

@Composable
fun HomeView() {
    val vm: HomeViewModel = viewModel()
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ActionBarView()

                AppGroupsView(
                    modifier = Modifier,
                )
            }

            BackHandler(
                enabled = state == HomeViewState.CreateAppGroup,
                onBack = vm::onBackClick
            )

            FloatingActionButton(
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = vm::onCreateAppGroupClick
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
                visible = state == HomeViewState.CreateAppGroup,
                enter = slideInVertically {
                    it
                },
                exit = slideOutVertically {
                    it
                }
            ) {
                CreateAppGroupView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    onBackClick = vm::onBackClick
                )
            }
        }
    }
}



