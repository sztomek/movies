package hu.sztomek.movies.domain

interface ResourceHelper {

    fun getString(stringId: Int): String
    fun getStringFormatted(stringId: Int, formatArgs: Array<String>): String
    fun dimensionInPixes(dimensionId: Int): Int

}