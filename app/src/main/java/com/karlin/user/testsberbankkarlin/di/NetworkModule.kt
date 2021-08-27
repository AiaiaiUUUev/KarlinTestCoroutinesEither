package com.karlin.user.testsberbankkarlin.di

import androidx.collection.ArrayMap
import com.karlin.user.feature_currencyconverter.data.CurrencyApi
import com.karlin.user.testsberbankkarlin.BuildConfig
import com.squareup.moshi.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException
import javax.inject.Singleton


/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.testsberbankkarlin.di
 */

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Timber.tag("HttpLoggingInterceptor").i("%s", message)
                    }
                }
                )
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(CurrencyApi.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideJsonConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

}