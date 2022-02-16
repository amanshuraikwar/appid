package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.data.AppUninstaller
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.AppGroupView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@Composable
fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    id: String,
) {
    val vm: AppGroupDetailViewModel = viewModel()
    val state by vm.state.collectAsState()

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(
        key1 = id
    ) {
        vm.init(id)
    }

    AppGroupDetailView(
        modifier = modifier,
        state = state,
        onDeleteClick = { appGroup ->
            scope.launch(Dispatchers.Default) {
                appGroup.apps.forEach { app ->
                    if (isActive) {
                        val result = AppUninstaller.uninstall(
                            ctx as ComponentActivity,
                            app.packageName
                        )
                    }
                }
            }
        }
    )
}

@Composable
internal fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    state: AppGroupDetailState,
    onDeleteClick: (AppGroup) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.statusBarsPadding()
        ) {
            when (state) {
                is AppGroupDetailState.AppGroupNotFound -> {
                    Text(text = "not found")
                }
                AppGroupDetailState.Loading -> {
                    Text(text = "loading")
                }
                is AppGroupDetailState.Success -> {
                    Column {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = state.appGroup.name,
                            style = MaterialTheme.typography.h3
                        )

                        AppGroupView(appGroup = state.appGroup)

                        ActionButton(
                            text = "Delete All Apps",
                            onClick = {
                                onDeleteClick(state.appGroup)
                            }
                        )
                    }
                }
            }
        }
    }
}