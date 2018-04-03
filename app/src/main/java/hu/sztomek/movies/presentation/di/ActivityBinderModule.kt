package hu.sztomek.movies.presentation.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hu.sztomek.movies.presentation.screen.search.SearchActivity
import hu.sztomek.movies.presentation.screen.search.SearchModule

@Module
interface ActivityBinderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(SearchModule::class))
    fun bindSearchActivity(): SearchActivity

}