package com.example.test_itunes_search.di.module

import com.example.test_itunes_search.BuildConfig
import com.example.test_itunes_search.domain.NetworkUseCase
import com.example.test_itunes_search.repository.networkapi.NetworkApi
import com.example.test_itunes_search.repository.networkapi.NetworkRepository
import com.example.test_itunes_search.utils.Constants

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideCommunicator(networkApi: NetworkApi): NetworkRepository {
        return NetworkRepository(networkApi)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkApi {
        return retrofit.create<NetworkApi>(NetworkApi::class.java)
    }

    @Provides
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(API_URL).build()
    }

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val builder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    fun provideNetworkUseCase(networkRepository: NetworkRepository): NetworkUseCase {
        return NetworkUseCase(networkRepository)
    }

    companion object {
        private const val API_URL = "https://itunes.apple.com/"
    }
}