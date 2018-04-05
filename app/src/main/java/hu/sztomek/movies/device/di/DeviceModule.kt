package hu.sztomek.movies.device.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import hu.sztomek.movies.device.ResourceHelperImpl
import hu.sztomek.movies.domain.ResourceHelper

@Module
class DeviceModule {

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideResourceHelper(resources: Resources): ResourceHelper {
        return ResourceHelperImpl(resources)
    }

}