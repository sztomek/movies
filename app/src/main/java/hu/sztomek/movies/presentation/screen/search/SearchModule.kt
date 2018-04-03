package hu.sztomek.movies.presentation.screen.search

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides

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

}