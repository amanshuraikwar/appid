package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged {
                    buttonWidth = it.width
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            color = if (state is CreateAppGroupState.Success
                && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
            ) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.onSurface.disabled
            },
            shape = RoundedCornerShape(50)
        ) {
            Text(
                modifier = Modifier
                    .let {
                        if (state is CreateAppGroupState.Success
                            && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
                        ) {
                            it.clickable {
                                onCreateGroupClick(state.packageName)
                            }
                        } else {
                            it
                        }
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                text = "Create Group",
                style = MaterialTheme.typography.button,
                color = if (state is CreateAppGroupState.Success
                    && state.canCreateAppGroup is CreateAppGroupState.CanCreateAppGroup.Yes
                ) {
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.onSurface.medium
                },
            )
        }
    }
}