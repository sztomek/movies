package hu.sztomek.movies.presentation.navigation

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import hu.sztomek.movies.presentation.screen.details.MovieDetailsActivity

class NavigatorImpl(private var activity: AppCompatActivity? = null) : Navigator {

    override fun takeActivity(activity: AppCompatActivity) {
        this.activity = activity
    }

    override fun close() {
        activity?.finish()
    }

    override fun goDetails(movieId: Int) {
        if (activity == null) {
            throw NavigationException("Activity is not set!")
        }
        activity!!.startActivity(MovieDetailsActivity.starter(activity!!, movieId))
    }

    override fun back() {
        close() // currently just simply closes
    }

    override fun openInBrowser(url: String) {
        if (activity == null) {
            throw NavigationException("Activity is not set!")
        }
        activity!!.startActivity(
                Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(url))
        )
    }
}