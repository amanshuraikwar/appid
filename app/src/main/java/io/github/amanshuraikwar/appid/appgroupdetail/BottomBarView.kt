package io.github.amanshuraikwar.appid.appgroupdetail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.ProgressView

@Composable
internal fun BottomBarView(
    modifier: Modifier = Modifier,
    state: AppGroupDetailState.Success,
    onDeleteClick: () -> Unit
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
                is AppGroupDetailState.Success.DeletionInProgress -> state.progressText
                is AppGroupDetailState.Success.Idle -> ""
            },
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .onSizeChanged {
                    buttonWidth = it.width
                }
                .padding(16.dp)
        ) {
            when (state) {
                is AppGroupDetailState.Success.DeletionInProgress -> {
                    ProgressView(
                        progress = state.progress
                    )
                }
                is AppGroupDetailState.Success.Idle -> {
                    ActionButton(
                        Modifier.fillMaxWidth(),
                        text = "Delete All Apps",
                        onClick = onDeleteClick
                    )
                }
            }
        }
    }
}