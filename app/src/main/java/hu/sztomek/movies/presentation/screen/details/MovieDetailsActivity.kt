package hu.sztomek.movies.presentation.screen.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import hu.sztomek.movies.R
import hu.sztomek.movies.presentation.common.BaseActivity
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.model.UiModel

class MovieDetailsActivity : BaseActivity<UiModel>() {

    companion object {
        private const val KEY_MOVIE_ID = "movies.movieId"

        fun starter(caller: Context, movieId: Int): Intent {
            val intent = Intent(caller, MovieDetailsActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            return intent
        }

        private fun getMovieIdFromIntent(intent: Intent?): Int? {
            return intent?.getIntExtra(KEY_MOVIE_ID, -1)
        }
    }

    override fun initUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getDefaultInitialState(): UiModel {
        return MovieDetailsUiModel(getMovieIdFromIntent(intent)!!)
    }

    override fun getViewModelClass(): Class<out BaseViewModel> {
        return MovieDetailsViewModel::class.java
    }

    override fun render(it: UiState?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}
