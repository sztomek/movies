package hu.sztomek.movies.device.image

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import hu.sztomek.movies.R


@GlideModule
class CustomGlide : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.INFO)
        builder.setDefaultRequestOptions(
                RequestOptions()
                        .error(R.drawable.ic_broken)
                        .centerCrop()
        )
    }

}