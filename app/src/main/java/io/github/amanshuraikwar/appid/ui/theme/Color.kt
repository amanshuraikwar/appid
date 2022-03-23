package io.github.amanshuraikwar.appid.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val white = Color(0xFFffffff)
val blueGrey50 = Color(0xFFeef0f2)
val redLighter = Color(0xFFE57373)
val red = Color(0xFFC62828)
val blueGreyDark = Color(0xFF0C0F11)
val gray900 = Color(0xFF212121)

val aqua = Color(0xFF9EF1ED)
val aquaVariant = Color(0xFF6EF0EC)
val aquaDark = Color(0xFF2AC7B9)
val cementDark = Color(0xFF37474F)

val seaBuckthorn = Color(0xFFF57F17)
val seaBuckthornLight = Color(0xFFFFF176)

val Colors.remove: Color
    get() = if (isLight) {
        seaBuckthorn
    } else {
        seaBuckthornLight
    }

val Colors.onRemove: Color
    get() = if (isLight) {
        white
    } else {
        cementDark
    }

val Color.medium: Color
    get() = this.copy(alpha = 0.74f)

val Color.disabled: Color
    get() = this.copy(alpha = 0.38f)

val Colors.outline: Color
    get() = this.onSurface.copy(alpha = 0.12f)