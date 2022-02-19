package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import io.github.amanshuraikwar.appid.AppIconImageData
import io.github.amanshuraikwar.appid.AppIconLayer
import io.github.amanshuraikwar.appid.coilOverloadedImageLoader

@Composable
fun AppIconView(
    modifier: Modifier = Modifier,
    packageName: String,
    shape: Shape = MaterialTheme.shapes.small
) {
    Box(
        modifier
            .clip(shape)
    ) {
        AsyncImage(
            modifier = Modifier
                .matchParentSize(),
            model = AppIconImageData(
                packageName = packageName,
                layer = AppIconLayer.BACKGROUND
            ),
            contentDescription = "app icon $packageName",
            imageLoader = LocalContext.current.coilOverloadedImageLoader
        )

        AsyncImage(
            modifier = Modifier
                .matchParentSize(),
            model = AppIconImageData(
                packageName = packageName,
                layer = AppIconLayer.FOREGROUND
            ),
            contentDescription = "app icon $packageName",
            imageLoader = LocalContext.current.coilOverloadedImageLoader
        )
    }

}