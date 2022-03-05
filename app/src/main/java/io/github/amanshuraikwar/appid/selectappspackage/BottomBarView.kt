package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.ActionButton

@Composable
internal fun BottomBarView(
    modifier: Modifier = Modifier,
    onSelectClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Divider()

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