package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.rememberAppIdIndication

@Suppress("UNUSED_PARAMETER")
@Composable
fun ActionBar(
    state: AppGroupDetailState,
    onBackClick: () -> Unit,
    onGridViewClick: () -> Unit,
    onListViewClick: () -> Unit,
    onDeleteAppGroupClick: (AppGroup) -> Unit,
) {
    CompositionLocalProvider(
        LocalIndication provides rememberAppIdIndication(
            color = MaterialTheme.colors.primary
        )
    ) {
        ActionBarView(
            elevation = 0.dp,
            surfaceColor = MaterialTheme.colors.onPrimary
        ) {
            Column {
                Box(
                    Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        imageVector = Icons.TwoTone.ArrowBack,
                        contentDescription = "Back",
                        onClick = onBackClick,
                        foregroundColor = MaterialTheme.colors.primary
                    )

                    if (state is AppGroupDetailState.Success) {
                        // commenting this feature out due to performance issues
                        /*
                        IconButton(
                            modifier = Modifier
                                .padding(end = 56.dp)
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = when (state.appDisplayType) {
                                AppGroupDetailState.AppDisplayType.GRID -> {
                                    Icons.TwoTone.GridView
                                }
                                AppGroupDetailState.AppDisplayType.LIST -> {
                                    Icons.TwoTone.ViewList
                                }
                            },
                            contentDescription = "Grid View",
                            foregroundColor = MaterialTheme.colors.primary,
                            onClick = when (state.appDisplayType) {
                                AppGroupDetailState.AppDisplayType.GRID -> onListViewClick
                                AppGroupDetailState.AppDisplayType.LIST -> onGridViewClick
                            }
                        )
                         */

                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = Icons.TwoTone.Delete,
                            contentDescription = "Delete",
                            onClick = {
                                onDeleteAppGroupClick(state.appGroup)
                            },
                            foregroundColor = MaterialTheme.colors.primary
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(
                            top = 48.dp,
                            start = 16.dp,
                            bottom = 24.dp,
                            end = 16.dp
                        ),
                    text = when (state) {
                        is AppGroupDetailState.AppGroupNotFound -> "App group not found"
                        AppGroupDetailState.Loading -> ""
                        is AppGroupDetailState.Success -> state.appGroup.name
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}