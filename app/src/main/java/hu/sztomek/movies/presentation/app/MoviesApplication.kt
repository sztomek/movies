package hu.sztomek.movies.presentation.app

import android.app.Application
import hu.sztomek.movies.presentation.di.DaggerAppComponent
import timber.log.Timber

class MoviesApplication : Application() {

    val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent.inject(this)
    }

}