package hu.sztomek.movies.presentation.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.sztomek.movies.presentation.screen.search.SearchViewModel

@Module
interface ViewModelBinderModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: MoviesViewModelFactory): ViewModelProvider.Factory
}