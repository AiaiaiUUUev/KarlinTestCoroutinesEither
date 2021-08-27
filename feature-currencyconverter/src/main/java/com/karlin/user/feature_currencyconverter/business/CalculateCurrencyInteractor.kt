package com.karlin.user.feature_currencyconverter.business

import com.example.dispatchers.DispatchersFactory
import com.karlin.user.common.Params
import com.karlin.user.common.usecase.SuspendUseCase
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 17.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.business
 */

class CalculateCurrencyInteractor @Inject constructor(
    dispatcher: DispatchersFactory,
) : SuspendUseCase<Double>(dispatcher) {

    override suspend fun sourceImpl(params: Params): Double {
        val value = params.get<Double>(PARAM_KEY_ENTERED_VALUE)
        val target = params.get<Double>(PARAM_KEY_TARGET)
        return if (value != null && target != null) value * getBigDecimalTarget(target)
        else throw CurrencyFailure.ExceptionOnCalculate
    }

    private fun getBigDecimalTarget(target: Double) =
        BigDecimal(target).setScale(3, RoundingMode.HALF_UP).toDouble()

    companion object {
        const val PARAM_KEY_ENTERED_VALUE = "entered value"
        const val PARAM_KEY_TARGET = "target"
    }
}