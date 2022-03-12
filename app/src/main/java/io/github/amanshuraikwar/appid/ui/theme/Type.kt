package io.github.amanshuraikwar.appid.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.amanshuraikwar.appid.R

private val RubikFontFamily = FontFamily(
    Font(R.font.rubik),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_medium, FontWeight.Medium)
)

private val RobotoMonoFontFamily = FontFamily(
    Font(R.font.roboto_mono),
)

private val RobotoSlabFontFamily = FontFamily(
    Font(R.font.roboto_slab_medium, FontWeight.Medium),
)

val AppIdTypography = Typography(
    defaultFontFamily = RubikFontFamily,
    h5 = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h3 = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = RobotoMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.4.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeight = 22.sp
    ),
)

val Typography.appName: TextStyle
    get() = subtitle1.copy(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp",
        letterSpacing = 2.sp
    )

val Typography.appNameLarge: TextStyle
    get() = h3.copy(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp",
        letterSpacing = 12.sp
    )

val Typography.packageName: TextStyle
    get() = body2.copy(
        fontFamily = RobotoMonoFontFamily,
        fontFeatureSettings = "smcp",
    )