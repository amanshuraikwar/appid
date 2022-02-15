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

private val CabinFontFamily = FontFamily(
    Font(R.font.cabin),
)

private val RobotoMonoFontFamily = FontFamily(
    Font(R.font.roboto_mono),
)

val AppIdTypography = Typography(
    defaultFontFamily = RubikFontFamily,
    h3 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    button = TextStyle(
        fontFamily = RobotoMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp",
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        fontFamily = CabinFontFamily
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        fontFamily = CabinFontFamily
    )
)

val Typography.body1Bold: TextStyle
    get() = body1.copy(fontWeight = FontWeight.Bold)

val Typography.h6Bold: TextStyle
    get() = h6.copy(fontWeight = FontWeight.Bold)

val Typography.h4Bold: TextStyle
    get() = h4.copy(fontWeight = FontWeight.Bold)

val Typography.appName: TextStyle
    get() = subtitle1.copy(
        fontFamily = RobotoMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontFeatureSettings = "smcp"
    )