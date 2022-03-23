package io.github.amanshuraikwar.appid

import android.content.Context
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import coil.Coil
import coil.ImageLoader
import kotlinx.coroutines.Dispatchers

enum class AppIconLayer {
    FOREGROUND, BACKGROUND
}

data class AppIconImageData(
    val packageName: String,
    val layer: AppIconLayer
)

inline val Context.coilOverloadedImageLoader: ImageLoader
    get() = CoilOverloads.imageLoader(this)

object CoilOverloads {
    private var imageLoader: ImageLoader? = null

    fun imageLoader(context: Context): ImageLoader {
        return imageLoader ?: newImageLoader(context = context)
    }

    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val newImageLoader = Coil.imageLoader(context = context)
            .newBuilder()
            .components {
                add { chain ->
                    val data = chain.request.data
                    // get the icon drawable from package name here
                    // instead of in a composable scope
                    if (data is AppIconImageData) {
                        val drawable = chain
                            .request
                            .context
                            .packageManager
                            .getApplicationIcon(data.packageName)

                        val newData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (drawable is AdaptiveIconDrawable) {
                                when (data.layer) {
                                    AppIconLayer.FOREGROUND -> drawable.foreground
                                    AppIconLayer.BACKGROUND -> drawable.background
                                }
                            } else {
                                when (data.layer) {
                                    AppIconLayer.FOREGROUND -> drawable
                                    AppIconLayer.BACKGROUND -> ColorDrawable()
                                }
                            }
                        } else {
                            when (data.layer) {
                                AppIconLayer.FOREGROUND -> drawable
                                AppIconLayer.BACKGROUND -> ColorDrawable()
                            }
                        }

                        chain.proceed(
                            chain.request
                                .newBuilder()
                                .data(newData)
                                .build()
                        )
                    } else {
                        chain.proceed(chain.request)
                    }
                }
            }
            .interceptorDispatcher(Dispatchers.Default)
            .build()

        imageLoader = newImageLoader
        return newImageLoader
    }
}