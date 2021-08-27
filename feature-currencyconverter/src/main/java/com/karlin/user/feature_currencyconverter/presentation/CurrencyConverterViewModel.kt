package com.karlin.user.feature_currencyconverter.presentation

import com.karlin.user.common.extension.lazyUnsafe
import com.karlin.user.common.flows.MutableDataStateFlow
import com.karlin.user.common.viewmodel.BaseCoroutineViewModel
import com.karlin.user.common.viewmodel.DataState
import com.karlin.user.feature_currencyconverter.business.CalculateCurrencyInteractor
import com.karlin.user.feature_currencyconverter.business.GetCurrenciesInteractor
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.presentation
 */

class CurrencyConverterViewModel @Inject constructor(
    private val getCurrenciesInteractor: GetCurrenciesInteractor,
    private val calculateCurrencyInteractor: CalculateCurrencyInteractor
) : BaseCoroutineViewModel() {

    lateinit var currencyEntity: CurrencyEntity
    val currencyEntityReady
        get() = ::currencyEntity.isInitialized

    private val _currencies: MutableStateFlow<DataState<CurrencyEntity>> = MutableDataStateFlow()
    internal val currencies: StateFlow<DataState<CurrencyEntity>> = _currencies

    private val _calculatedValue = MutableDataStateFlow<Double>()
    internal val calculatedValue: StateFlow<DataState<Double>> = _calculatedValue

    fun loadCurrencies(base: String? = null) = launch {
        val result = getCurrenciesInteractor.source { GetCurrenciesInteractor.PARAM_KEY_BASE of base }
        // todo ЗДЕСЬ МОЖНО В ОДИН ФЛОУ ОШПИПКУ ПРОБРАСЫВАТЬ В ДРУГОЙ СУКСЕС
        //  result.either(view::showErrorDialog, ::handleGetCurrencies)
    }

    fun calculateCurrency(enteredValue: Double?, target: Double?) =
        launch {
            val result = calculateCurrencyInteractor.source {
                CalculateCurrencyInteractor.PARAM_KEY_ENTERED_VALUE of enteredValue
                CalculateCurrencyInteractor.PARAM_KEY_TARGET of target
            }
            _calculatedValue.value = DataState.success(result)
        }

}