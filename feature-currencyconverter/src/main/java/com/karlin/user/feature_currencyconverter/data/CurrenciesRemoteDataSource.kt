package com.karlin.user.feature_currencyconverter.data

import android.accounts.NetworkErrorException
import com.karlin.user.common.exception.Failure
import com.karlin.user.common.functional.Either
import com.karlin.user.data.db.CurrenciesDaoImpl
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity.Companion.EMPTY_CURRENCY_ENTITY
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.data
 */

class CurrenciesRemoteDataSource @Inject constructor(
    private val api: CurrencyApi
) {

    suspend fun getCurrencies(base: String): Either<Failure, CurrencyEntity?> =
        request(
            api.getCurrencies(base),
            { currencyEntity -> currencyEntity },
            EMPTY_CURRENCY_ENTITY
        )

    private fun <T, R> request(
        response: Response<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(CurrencyFailure.ExceptionServer)
            }
        } catch (exception: Throwable) {
            Either.Left(CurrencyFailure.ExceptionServer)
        }
    }
}