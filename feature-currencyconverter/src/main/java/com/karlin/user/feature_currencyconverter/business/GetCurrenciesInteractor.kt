package com.karlin.user.feature_currencyconverter.business

import com.example.dispatchers.DispatchersFactory
import com.karlin.user.common.Params
import com.karlin.user.common.exception.Failure
import com.karlin.user.common.functional.Either
import com.karlin.user.common.usecase.SuspendUseCase
import com.karlin.user.feature_currencyconverter.data.CurrenciesRepository
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.business
 */

class GetCurrenciesInteractor @Inject constructor(
    dispatcher: DispatchersFactory,
    private val repository: CurrenciesRepository,
) : SuspendUseCase<Either<Failure, CurrencyEntity?>>(dispatcher) {

    override suspend fun sourceImpl(params: Params): Either<Failure, CurrencyEntity?> {
        val base = params.get<String>(PARAM_KEY_BASE)
        return repository.getCurrencies(base)
    }

    companion object {
        const val PARAM_KEY_BASE = "param key base"
    }
}