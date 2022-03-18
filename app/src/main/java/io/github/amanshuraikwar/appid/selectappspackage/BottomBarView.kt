package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
internal fun BottomBarView(
    modifier: Modifier = Modifier,
    state: SelectAppsPackageState,
    onSelectClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider()

        when (state) {
            SelectAppsPackageState.Idle -> {
                // do nothing
            }
            is SelectAppsPackageState.NoApps -> {
                // do nothing
            }
            is SelectAppsPackageState.Success -> {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                        .padding(
                            top = 8.dp
                        ),
                    text = "${state.selectedAppCount.value} APPS SELECTED",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.medium
                )
            }
        }

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            text = "Add All Apps",
            onClick = {
                onSelectClick()
            }
        )
    }
}