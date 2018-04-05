package hu.sztomek.movies.presentation.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import hu.sztomek.movies.data.di.DataModule
import hu.sztomek.movies.device.di.DeviceModule
import hu.sztomek.movies.presentation.app.MoviesApplication

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        DeviceModule::class,
        NavigatorModule::class,
        ViewModelBinderModule::class,
        ActivityBinderModule::class
))
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: MoviesApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MoviesApplication): Builder
        fun build(): AppComponent
    }

}