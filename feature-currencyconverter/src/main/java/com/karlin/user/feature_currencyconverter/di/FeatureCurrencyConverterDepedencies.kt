package com.karlin.user.feature_currencyconverter.di

import android.content.Context
import com.example.dispatchers.DispatchersFactory
import com.google.gson.Gson
import com.karlin.user.common.FeatureDependencies
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.di
 */

interface FeatureCurrencyConverterDepedencies : FeatureDependencies {
    val retrofit: Retrofit
    val moshi: Moshi
    val context: Context
    val dispatchersFactory: DispatchersFactory
}