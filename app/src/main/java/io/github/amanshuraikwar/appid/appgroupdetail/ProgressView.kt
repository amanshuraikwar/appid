package io.github.amanshuraikwar.appid.appgroupdetail

import android.graphics.Matrix
import android.graphics.Path
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.theme.disabled

// Inspired from: https://gist.github.com/c5inco/7583dccb41e0ef55fe89d411dd99c433
@Composable
fun ProgressView(
    modifier: Modifier = Modifier,
    progress: Float = 0.2f,
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colors.primary,
    bgColor: Color = MaterialTheme.colors.primary.disabled,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(
        3200,
        easing = LinearEasing
    )
) {
    val transition: InfiniteTransition = rememberInfiniteTransition()
    val baseWidth = with(LocalDensity.current) { 24.dp.toPx() }
    val baseHeight = with(LocalDensity.current) { 24.dp.toPx() }
    val width = with(LocalDensity.current) { strokeWidth.toPx() }
    val radius = baseWidth / 2 - width / 2
    val circumference = (2 * Math.PI * radius).toFloat()
    val dashLength: Float by animateFloatAsState(targetValue = circumference * progress)

    val phaseRotate by transition.animateFloat(
        initialValue = -circumference,
        targetValue = circumference,
        animationSpec = infiniteRepeatable(
            animation = animationSpec
        )
    )

    Canvas(
        modifier
            .size(24.dp)
    ) {
        val path = Path()
        path.addCircle(baseWidth / 2, baseHeight / 2, radius, Path.Direction.CW)
        path.close()

        val bgPath = Path()
        bgPath.addCircle(baseWidth / 2, baseHeight / 2, radius, Path.Direction.CW)
        bgPath.close()

        drawPath(
            path = path
                .apply {
                    transform(
                        Matrix().apply {
                            setScale(size.width / baseWidth, size.height / baseHeight)
                        }
                    )
                }
                .asComposePath(),
            color = color,
            style = Stroke(
                width = width,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashLength, circumference - dashLength),
                    phaseRotate
                )
            )
        )

        drawPath(
            path = path
                .apply {
                    transform(
                        Matrix().apply {
                            setScale(size.width / baseWidth, size.height / baseHeight)
                        }
                    )
                }
                .asComposePath(),
            color = bgColor,
            style = Stroke(
                width = width,
            )
        )
    }
}