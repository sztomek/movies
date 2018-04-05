package hu.sztomek.movies.data.di

import dagger.Module
import dagger.Provides
import hu.sztomek.movies.data.DataSourceImpl
import hu.sztomek.movies.data.RestApi
import hu.sztomek.movies.domain.data.DataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Module
class DataModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "https://api.themoviedb.org/3/"
    }

    @Provides
    @Named("posterUrl")
    fun providePosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500"
    }

    @Provides
    @Named("apiKey")
    fun provideApiKey(): String {
        return "43a7ea280d085bd0376e108680615c7f"
    }

    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor({ message -> Timber.d(message) })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    fun provideRetrofit(@Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    fun provideDataSource(restApi: RestApi, @Named("apiKey") apiKey: String, @Named("posterUrl") posterUrl: String): DataSource {
        return DataSourceImpl(apiKey, restApi, posterUrl)
    }

}