package hu.sztomek.movies.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.sztomek.movies.presentation.app.MoviesApplication

@Module
class AppModule {

    @Provides
    fun provideAppContext(application: MoviesApplication): Context {
        return application
    }

}