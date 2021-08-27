package com.karlin.user.feature_currencyconverter.data

import com.karlin.user.common.exception.Failure
import com.karlin.user.common.functional.Either
import com.karlin.user.data.db.CurrenciesDaoImpl
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import io.reactivex.Single
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.data
 */

class CurrenciesLocalDataSource @Inject constructor(private val dao: CurrenciesDaoImpl) {

    suspend fun getCurrencies(base: String): Either<Failure, CurrencyEntity?> =
        Either.Right(dao.getCurrencies(base))

    suspend fun saveCurrencies(currencyEntity: CurrencyEntity) = dao.insertOrUpdate(currencyEntity)

}