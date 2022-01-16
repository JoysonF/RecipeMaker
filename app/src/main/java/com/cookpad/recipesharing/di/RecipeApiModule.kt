package com.cookpad.recipesharing.di

import com.cookpad.recipesharing.BuildConfig
import com.cookpad.recipesharing.data.source.remote.api.RecipeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeApiModule {

    @Provides
    @Named("baseUrl")
    fun providesBaseUrl(): String {
        return BuildConfig.API_BASE
    }

    @Provides
    @Singleton
    fun provideRecipeApiService(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient
    ): RecipeApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            client.addInterceptor(logging)
        }
        return client.build()
    }
}
