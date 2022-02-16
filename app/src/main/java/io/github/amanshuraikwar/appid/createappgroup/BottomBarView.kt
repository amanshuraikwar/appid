package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
internal fun BottomBarView(
    modifier: Modifier = Modifier,
    state: CreateAppGroupState,
    onCreateGroupClick: (String) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        var buttonWidth by remember { mutableStateOf(0) }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(
                    end = with(LocalDensity.current) {
                        buttonWidth.toDp()
                    }
                ),
            text = when (state) {
                CreateAppGroupState.Loading -> ""
                is CreateAppGroupState.NoApps -> ""
                is CreateAppGroupState.Success -> {
                    if (state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.No) {
                        state.canCreateAppGroup.why
                    } else {
                        ""
                    }
                }
            },
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2
        )

        ActionButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged {
                    buttonWidth = it.width
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            text = "Create Group",
            bgColor = if (state is CreateAppGroupState.Success
                && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
            ) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.onSurface.disabled
            },
            textColor = if (state is CreateAppGroupState.Success
                && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
            ) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.onSurface.medium
            },
            enabled = state is CreateAppGroupState.Success
                    && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes,
            onClick = {
                if (state is CreateAppGroupState.Success
                    && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
                ) {
                    onCreateGroupClick(state.packageName)
                }
            }
        )
    }
}