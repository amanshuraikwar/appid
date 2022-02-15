package io.github.amanshuraikwar.appid.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import io.github.amanshuraikwar.appid.AppIconImageData
import io.github.amanshuraikwar.appid.coilOverloadedImageLoader

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