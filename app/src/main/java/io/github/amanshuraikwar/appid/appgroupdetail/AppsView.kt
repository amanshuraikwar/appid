package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.util.rememberAppLauncher

@Composable
internal fun AppsView(
    modifier: Modifier = Modifier,
    state: AppGroupDetailState.Success,
    onAppDeleteClick: (AppGroup, App) -> Unit,
) {
    val appLauncher = rememberAppLauncher()

    when (state.appDisplayType) {
        AppGroupDetailState.AppDisplayType.GRID -> {
            AppGroupView(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp, end = 16.dp),
                appGroup = state.appGroup,
                onAppClick = appLauncher::launch,
                appIconSize = 48.dp
            )
        }
        AppGroupDetailState.AppDisplayType.LIST -> {
            LazyColumn(
                modifier
                    .fillMaxWidth(),
            ) {
                items(
                    items = state.appGroup.apps,
                    key = { it.packageName }
                ) { item ->
                    AppView(
                        app = item,
                        onClick = appLauncher::launch,
                        appIconSize = 40.dp,
                        onDeleteClick = {
                            onAppDeleteClick(state.appGroup, it)
                        }
                    )
                }
            }
        }
    }
}