package hu.sztomek.movies.presentation.di

import dagger.Module
import dagger.Provides
import hu.sztomek.movies.presentation.navigation.Navigator
import hu.sztomek.movies.presentation.navigation.NavigatorImpl

@Module
class NavigatorModule {

    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }

}