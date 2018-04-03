package hu.sztomek.movies.presentation.screen.search

import android.support.v7.widget.RecyclerView
import android.widget.Toast
import hu.sztomek.movies.R
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.presentation.common.BaseActivity
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.model.SearchUiModel
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchUiModel>() {

    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun initUi() {
        setContentView(R.layout.activity_search)
        rvList.layoutManager = layoutManager
        rvList.adapter = moviesAdapter
        moviesAdapter.clickListener = {
            // TODO navigate
            Toast.makeText(this, "Clicked movie: ${it.title}", Toast.LENGTH_SHORT).show()
        }

        btnSearch.setOnClickListener {
            viewModel.sendAction(
                SearchAction(
                    etQuery.text.toString(),
                    1 // TODO get actual page
                )
            )
        }
    }

    override fun getDefaultInitialState(): SearchUiModel {
        return SearchUiModel("", 1)
    }

    override fun getViewModelClass(): Class<out BaseViewModel> {
        return SearchViewModel::class.java
    }

    override fun render(it: UiState?) {
        when (it) {
            is UiState.LoadingState -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            is UiState.ErrorState -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            is UiState.SuccessState -> {
                moviesAdapter.setList((it.data as SearchUiModel).searchResults)
            }
        }
    }
}