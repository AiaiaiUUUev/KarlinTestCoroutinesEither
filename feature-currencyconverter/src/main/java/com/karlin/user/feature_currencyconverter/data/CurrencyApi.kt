package com.karlin.user.feature_currencyconverter.data

import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.data
 */
interface CurrencyApi {
    companion object {
        const val BASE_URL = "https://api.exchangeratesapi.io/"
        private const val LATEST_CURRENCY = "latest"
    }

    @GET(LATEST_CURRENCY)
    suspend fun getCurrencies(@Query("base") base: String): Response<CurrencyEntity>
}