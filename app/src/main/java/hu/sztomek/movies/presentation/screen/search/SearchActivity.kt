package hu.sztomek.movies.presentation.screen.search

import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.paginate.Paginate
import hu.sztomek.movies.R
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.presentation.common.BaseActivity
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiError
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.model.SearchUiModel
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchUiModel>() {

    @Inject
    lateinit var layoutManager: RecyclerView.LayoutManager
    @Inject
    lateinit var itemDecoration: RecyclerView.ItemDecoration
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private lateinit var paginate: Paginate

    override fun initUi() {
        setContentView(R.layout.activity_search)
        rvList.layoutManager = layoutManager
        rvList.addItemDecoration(itemDecoration)
        rvList.adapter = moviesAdapter
        moviesAdapter.clickListener = {
            // TODO navigate
            Toast.makeText(this, "Clicked movie: ${it.title}", Toast.LENGTH_SHORT).show()
        }

        paginate = Paginate.with(rvList, object : Paginate.Callbacks {

            private fun getLastModel(): SearchUiModel {
                return viewModel.stateStream.value?.data as SearchUiModel
            }

            override fun onLoadMore() {
                viewModel.sendAction(SearchAction(getLastModel().query, getLastModel().page + 1))
            }

            override fun isLoading(): Boolean {
                return viewModel.stateStream.value is UiState.LoadingState
            }

            override fun hasLoadedAllItems(): Boolean {
                val lastModel = getLastModel()
                return lastModel.totalPages ?: 1 == 1 || lastModel.page == lastModel.totalPages
            }
        })
                .addLoadingListItem(false)
                .setLoadingTriggerThreshold(2)
                .build()

        srlList.setOnRefreshListener {
            viewModel.sendAction(
                    SearchAction(
                        etQuery.text.toString(),
                        1
                    )
            )
        }

        btnSearch.setOnClickListener {
            viewModel.sendAction(
                    SearchAction(
                            etQuery.text.toString(),
                            1 // should always reset current page
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
        enableUi(it !is UiState.LoadingState)
        srlList.isRefreshing = it is UiState.LoadingState

        when (it) {
            is UiState.LoadingState -> {
                tilQuery.error = null
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.ErrorState -> {
                when (it.uiError) {
                    is UiError.FormValidationUiError -> {
                        tilQuery.error = it.uiError.message
                    }
                    else -> {
                        Toast.makeText(this, it.uiError.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is UiState.IdleState -> {
                val searchUiModel = it.data as SearchUiModel
                moviesAdapter.setList(searchUiModel.searchResults)
                tvInfo.text = searchUiModel.statusText
            }
        }
    }

    private fun enableUi(enabled: Boolean) {
        btnSearch.isEnabled = enabled
        tilQuery.isEnabled = enabled
        etQuery.isEnabled = enabled
    }
}