package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    bgColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = bgColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            modifier = Modifier
                .let {
                    if (enabled) {
                        it.clickable(onClick = onClick)
                    } else {
                        it
                    }
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            text = text,
            style = MaterialTheme.typography.button,
            color = textColor,
        )
    }
}