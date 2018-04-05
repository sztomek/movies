package hu.sztomek.movies.presentation.navigation

import android.support.v7.app.AppCompatActivity

interface Navigator {

    fun takeActivity(activity: AppCompatActivity)

    fun close()
    fun goDetails(movieId: Int)
    fun back()

}