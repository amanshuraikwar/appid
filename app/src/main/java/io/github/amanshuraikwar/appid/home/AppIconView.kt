package io.github.amanshuraikwar.appid.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage

@Composable
fun AppIconView(
    modifier: Modifier = Modifier,
    packageName: String
) {
    AsyncImage(
        modifier = modifier,
        model = AppIconImageData(packageName),
        contentDescription = "app icon $packageName",
        imageLoader = LocalContext.current.coilOverloadedImageLoader
    )
}