package hu.sztomek.movies.presentation.screen.details

import android.content.Context
import android.content.Intent
import android.view.View
import hu.sztomek.movies.R
import hu.sztomek.movies.device.image.ImageViewTarget
import hu.sztomek.movies.domain.action.GetDetailsAction
import hu.sztomek.movies.domain.image.ImageLoader
import hu.sztomek.movies.presentation.common.BaseActivity
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.model.MovieDetailsUiModel
import hu.sztomek.movies.presentation.model.UiModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.content_details.view.*
import kotlinx.android.synthetic.main.content_error.view.*
import kotlinx.android.synthetic.main.content_loading.view.*
import javax.inject.Inject

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

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun initUi() {
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contentDetails.tvHomepage.setOnClickListener {
            navigator.openInBrowser(tvHomepage.text.toString())
        }

        viewModel.sendAction(GetDetailsAction(getMovieIdFromIntent(intent)!!))
    }

    override fun getDefaultInitialState(): UiModel {
        return MovieDetailsUiModel(getMovieIdFromIntent(intent)!!)
    }

    override fun getViewModelClass(): Class<out BaseViewModel> {
        return MovieDetailsViewModel::class.java
    }

    override fun render(it: UiState?) {
        when(it) {
            is UiState.LoadingState -> {
                contentDetails.visibility = View.GONE
                cardViewContainer.visibility = View.VISIBLE
                cardViewContainer.contentError.visibility = View.GONE
                cardViewContainer.contentLoading.visibility = View.VISIBLE
                cardViewContainer.contentLoading.tvLoadingMessage.text = it.message
            }
            is UiState.ErrorState -> {
                contentDetails.visibility = View.GONE
                cardViewContainer.visibility = View.VISIBLE
                cardViewContainer.contentError.visibility = View.VISIBLE
                cardViewContainer.contentLoading.visibility = View.GONE
                cardViewContainer.contentError.tvErrorMessage.text = it.uiError.message
            }
            is UiState.IdleState -> {
                contentDetails.visibility = View.VISIBLE
                cardViewContainer.visibility = View.GONE
                val movieDetailsUiModel = it.data as MovieDetailsUiModel
                contentDetails.tvTitle.text = movieDetailsUiModel.title
                contentDetails.tvPlayTime.text = movieDetailsUiModel.playTime
                contentDetails.tvRelease.text = movieDetailsUiModel.releaseDate
                contentDetails.tvCompany.text = movieDetailsUiModel.productionCompany
                contentDetails.tvBudget.text = movieDetailsUiModel.budget
                contentDetails.tvRating.text = movieDetailsUiModel.rating
                contentDetails.tvHomepage.text = movieDetailsUiModel.homePage
                contentDetails.tvHomepage.visibility = if (movieDetailsUiModel.homePage != null) View.VISIBLE else View.INVISIBLE
                imageLoader.loadAndDisplayAsync(movieDetailsUiModel.posterUrl, ImageViewTarget(contentDetails.ivPoster))
            }
        }
    }

}
