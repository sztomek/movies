package hu.sztomek.movies.presentation.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import hu.sztomek.movies.presentation.di.DaggerAppComponent
import timber.log.Timber

class MoviesApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}