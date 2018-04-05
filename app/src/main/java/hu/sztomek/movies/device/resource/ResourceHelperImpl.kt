package hu.sztomek.movies.device.resource

import android.content.res.Resources
import hu.sztomek.movies.domain.resource.ResourceHelper

class ResourceHelperImpl(private val resources: Resources) : ResourceHelper {

    override fun getString(stringId: Int): String {
        return resources.getString(stringId)
    }

    override fun getStringFormatted(stringId: Int, formatArgs: Array<String>): String {
        return resources.getString(stringId, *formatArgs)
    }

    override fun dimensionInPixes(dimensionId: Int): Int {
        return resources.getDimension(dimensionId).toInt()
    }
}