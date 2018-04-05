package hu.sztomek.movies.domain.image

interface ImageLoader {

    fun loadAndDisplayAsync(url: String?, imageLoaderTarget: ImageLoaderTarget)

}