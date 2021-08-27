package com.karlin.user.feature_currencyconverter.data

import com.karlin.user.common.exception.Failure
import com.karlin.user.common.functional.Either
import com.karlin.user.common.util.NetworkHandler
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.data
 */

const val RUB_CURRENCY = "RUB"

interface CurrenciesRepository {
    suspend fun getCurrencies(base: String?): Either<Failure, CurrencyEntity?>

    class Impl @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val currenciesLocalDataSource: CurrenciesLocalDataSource,
        private val currenciesRemoteDataSource: CurrenciesRemoteDataSource
    ) : CurrenciesRepository {
        override suspend fun getCurrencies(base: String?): Either<Failure, CurrencyEntity?> =
             when (networkHandler.isConnected) {
                true -> currenciesRemoteDataSource.getCurrencies(base ?: RUB_CURRENCY)
                else -> currenciesLocalDataSource.getCurrencies(base ?: RUB_CURRENCY)
            }
    }
}
