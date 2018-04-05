package hu.sztomek.movies.presentation.screen.search

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import dagger.Module
import dagger.Provides
import hu.sztomek.movies.R
import hu.sztomek.movies.domain.ResourceHelper

@Module
class SearchModule {

    @Provides
    fun provideMoviesAdapter(): MoviesAdapter {
        return MoviesAdapter()
    }

    @Provides
    fun provideLayoutManager(context: Context): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    fun provideItemDecoration(resourceHelper: ResourceHelper): RecyclerView.ItemDecoration {
        return object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                outRect?.set(
                        resourceHelper.dimensionInPixes(R.dimen.default_list_item_padding),
                        0,
                        resourceHelper.dimensionInPixes(R.dimen.default_list_item_padding),
                        resourceHelper.dimensionInPixes(R.dimen.default_list_item_padding)
                )
            }
        }
    }

}