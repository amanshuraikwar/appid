package io.github.amanshuraikwar.appid.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    bgColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    bgColorDisabled: Color = MaterialTheme.colors.onSurface.medium,
    textColorDisabled: Color = MaterialTheme.colors.surface.disabled,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    CompositionLocalProvider(
        LocalIndication provides rememberAppIdIndication(
            color = MaterialTheme.colors.onPrimary
        )
    ) {
        Surface(
            modifier = modifier,
            color = animateColorAsState(
                targetValue = if (enabled) {
                    bgColor
                } else {
                    bgColorDisabled
                }
            ).value,
            shape = MaterialTheme.shapes.small,
            elevation = 1.dp
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
                text = text.uppercase(),
                style = MaterialTheme.typography.button,
                color = animateColorAsState(
                    targetValue = if (enabled) {
                        textColor
                    } else {
                        textColorDisabled
                    }
                ).value,
                textAlign = TextAlign.Center
            )
        }
    }
}