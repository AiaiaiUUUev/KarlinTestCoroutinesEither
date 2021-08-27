package com.karlin.user.feature_currencyconverter.exception

import com.karlin.user.common.exception.Failure
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.exception
 */

sealed class CurrencyFailure : Failure.FeatureFailure() {
    object ExceptionServer : CurrencyFailure()
    object ExceptionNetworkConnection : CurrencyFailure()
    object ExceptionOnLoading : CurrencyFailure()
    object ExceptionOnCalculate : CurrencyFailure()
    object ExceptionCannotLoadData : CurrencyFailure()
}