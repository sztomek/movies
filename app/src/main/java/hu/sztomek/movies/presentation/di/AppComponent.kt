package hu.sztomek.movies.presentation.di

import dagger.BindsInstance
import dagger.Component
import hu.sztomek.movies.data.di.DataModule
import hu.sztomek.movies.presentation.app.MoviesApplication

@Component(modules = arrayOf(
        DataModule::class
))
interface AppComponent {

    fun inject(application: MoviesApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MoviesApplication): Builder
        fun build(): AppComponent
    }

}