package io.github.amanshuraikwar.appid.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

class AppIdIndication(
    private val color: Color
) : Indication {
    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed by interactionSource.collectIsPressedAsState()
        val isHovered by interactionSource.collectIsHoveredAsState()
        val isFocused by interactionSource.collectIsFocusedAsState()

        val color by animateColorAsState(
            targetValue = if (isPressed || isHovered || isFocused) {
                color
            } else {
                Color.Transparent
            }
        )

        return remember(interactionSource) {
            object : IndicationInstance {
                override fun ContentDrawScope.drawIndication() {
                    drawContent()
                    drawRect(color = color, size = size)
                }
            }
        }
    }
}

@Composable
fun rememberAppIdIndication(
    color: Color = MaterialTheme.colors.primary
): Indication {
    return remember(color) {
        AppIdIndication(color = color.copy(alpha = 0.15f))
    }
}