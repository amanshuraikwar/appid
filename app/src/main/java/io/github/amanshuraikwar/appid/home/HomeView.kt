package io.github.amanshuraikwar.appid.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.amanshuraikwar.appid.appgroupdetail.AppGroupDetailView
import io.github.amanshuraikwar.appid.appgroups.AppGroupsView
import io.github.amanshuraikwar.appid.createappgroup.CreateAppGroupView
import io.github.amanshuraikwar.appid.ui.ActionBarView

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView() {
    val vm: HomeViewModel = viewModel()
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            var actionBarHeight by remember { mutableStateOf(0) }

            ActionBarView(
                modifier = Modifier
                    .clickable {
                        actionBarHeight = 0
                    }
                    .onSizeChanged {
                        actionBarHeight = it.height
                    },
                elevation = 0.dp
            )

            AppGroupsView(
                paddingValues = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars,
                    additionalTop = with(LocalDensity.current) { actionBarHeight.toDp() },
                    additionalBottom = 128.dp
                ),
                onAppGroupClick = vm::onAppGroupClick
            )

            BackHandler(
                enabled =
                state == HomeViewState.CreateAppGroup
                        || state is HomeViewState.AppGroupDetail,
                onBack = vm::onBackClick
            )

            FloatingActionButton(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp),
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
                            .padding(16.dp)
                            .size(24.dp)
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
                    onCloseClick = vm::onBackClick
                )
            }

            AnimatedVisibility(
                visible = state is HomeViewState.AppGroupDetail,
                enter = slideInVertically {
                    it
                },
                exit = slideOutVertically {
                    it
                }
            ) {
                if (state is HomeViewState.AppGroupDetail) {
                    AppGroupDetailView(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        id = (state as HomeViewState.AppGroupDetail).id,
                        onBackClick = {
                            vm.onReturnedFromAppDetails(
                                appGroupId = (state as HomeViewState.AppGroupDetail).id
                            )
                            vm.onBackClick()
                        },
                    )
                }
            }
        }
    }
}



