package com.karlin.user.feature_currencyconverter.presentation

import androidx.lifecycle.ViewModel
import com.karlin.user.common.viewmodel.BaseViewModelFactory
import javax.inject.Inject

class CurrencyConverterViewModelFactory @Inject constructor(private val viewModel: CurrencyConverterViewModel) :
    BaseViewModelFactory<CurrencyConverterViewModel>() {

    override fun create(): ViewModel = viewModel

}