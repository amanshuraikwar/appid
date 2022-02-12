package io.github.amanshuraikwar.appid.home

import android.content.Context
import coil.Coil
import coil.ImageLoader
import kotlinx.coroutines.Dispatchers

data class AppIconImageData(val packageName: String)

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
                        chain.proceed(
                            chain.request
                                .newBuilder()
                                .data(
                                    chain
                                        .request
                                        .context
                                        .packageManager
                                        .getApplicationIcon(data.packageName)
                                )
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