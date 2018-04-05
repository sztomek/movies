package hu.sztomek.movies.device.image

import android.content.Context
import hu.sztomek.movies.domain.image.ImageLoader
import hu.sztomek.movies.domain.image.ImageLoaderTarget

class ImageLoaderImpl(private val context: Context) : ImageLoader {

    override fun loadAndDisplayAsync(url: String?, imageLoaderTarget: ImageLoaderTarget) {
        if (imageLoaderTarget !is ImageViewTarget) {
            throw ImageLoaderException("Only ImageViewTarget is supported currently")
        }

        GlideApp.with(context)
                .load(url)
                .into(imageLoaderTarget.imageView)
    }

}