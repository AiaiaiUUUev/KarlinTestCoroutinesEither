package com.karlin.user.feature_currencyconverter.di

import android.content.Context
import com.karlin.user.data.db.createCurrenciesDao
import com.karlin.user.feature_currencyconverter.data.CurrencyApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.di
 */

@Module
object CurrencyConverterModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit, moshi: Moshi): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideCurrenciesDao(context: Context) = createCurrenciesDao(context)

}