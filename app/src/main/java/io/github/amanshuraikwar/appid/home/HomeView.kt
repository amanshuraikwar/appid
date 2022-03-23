@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package io.github.amanshuraikwar.appid.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.amanshuraikwar.appid.about.AboutView
import io.github.amanshuraikwar.appid.acmNavigationBarsPadding
import io.github.amanshuraikwar.appid.appgroupdetail.AppGroupDetailView
import io.github.amanshuraikwar.appid.appgroups.AppGroupsView
import io.github.amanshuraikwar.appid.createappgroup.CreateAppGroupView
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.SwipeDismissView

@Composable
fun HomeView() {
    val vm: HomeViewModel = viewModel()
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AppIdScaffold(
                modifier = Modifier
                    .fillMaxSize(),
                actionBar = {
                    Column(
                        Modifier.clickable(onClick = vm::onAboutClick)
                    ) {
                        ActionBarView(
                            elevation = 0.dp
                        )

                        Divider()
                    }
                },
                bottomBar = {
                    Box {}
                }
            ) {
                AppGroupsView(
                    modifier = Modifier.fillMaxSize(),
                    paddingValues = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.navigationBars,
                        additionalBottom = 128.dp
                    ),
                    onAppGroupClick = vm::onAppGroupClick
                )
            }

            BackHandler(
                enabled =
                state == HomeViewState.CreateAppGroup
                        || state is HomeViewState.AppGroupDetail
                        || state is HomeViewState.About,
                onBack = vm::onBackClick
            )

            FloatingActionButton(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .acmNavigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = vm::onCreateAppGroupClick
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                )
            }

            SwipeDismissView(
                visible = state == HomeViewState.CreateAppGroup,
                enterFromTop = false,
                onDismiss = vm::onBackClick
            ) { modifier, _ ->
                CreateAppGroupView(
                    modifier = modifier,
                    onCloseClick = vm::onBackClick
                )
            }

            SwipeDismissView(
                visible = state is HomeViewState.AppGroupDetail,
                enterFromTop = false,
                onDismiss = vm::onBackClick,
            ) { modifier, animatedVisibilityScope ->
                var id: String? by remember {
                    mutableStateOf(null)
                }

                LaunchedEffect(key1 = animatedVisibilityScope.transition.currentState) {
                    if (
                        animatedVisibilityScope.transition.currentState == EnterExitState.Visible
                    ) {
                        id = (state as? HomeViewState.AppGroupDetail)?.id
                    }
                    if (
                        animatedVisibilityScope.transition.currentState == EnterExitState.PostExit
                    ) {
                        id = null
                    }
                }

                AppGroupDetailView(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    id = id,
                    onBackClick = {
                        vm.onReturnedFromAppDetails(
                            appGroupId = (state as HomeViewState.AppGroupDetail).id
                        )
                        vm.onBackClick()
                    },
                )
            }
        }

        SwipeDismissView(
            visible = state == HomeViewState.About,
            onDismiss = vm::onBackClick
        ) { modifier, _ ->
            AboutView(
                modifier,
                vm::onBackClick
            )
        }
    }
}



