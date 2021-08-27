package com.karlin.user.feature_currencyconverter.presentation

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.karlin.user.common.extension.hide
import com.karlin.user.common.extension.show
import com.karlin.user.common.findFeatureDependencies
import com.karlin.user.common.flows.collectIn
import com.karlin.user.common.flows.collectStateIn
import com.karlin.user.common.view.BaseFragment
import com.karlin.user.common.view.viewBindings
import com.karlin.user.common.viewmodel.onFailure
import com.karlin.user.common.viewmodel.onLoading
import com.karlin.user.common.viewmodel.onSuccess
import com.karlin.user.common.viewmodel.viewModels
import com.karlin.user.feature_currencyconverter.R
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.databinding.FragmentCurrencyConverterBinding
import com.karlin.user.feature_currencyconverter.di.DaggerCurrencyConverterComponent
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure.ExceptionNetworkConnection
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure.ExceptionCannotLoadData
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure.ExceptionOnCalculate
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure.ExceptionOnLoading
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.swiperefreshlayout.refreshes
import javax.inject.Inject


/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter
 */

class CurrencyConverterFragment : BaseFragment(R.layout.fragment_currency_converter) {

    @Inject
    lateinit var viewModelFactory: CurrencyConverterViewModelFactory
    private val viewModel by viewModels(::viewModelFactory)
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private val binding by viewBindings(FragmentCurrencyConverterBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerCurrencyConverterComponent
            .factory()
            .create(featureDependencies = findFeatureDependencies())
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        with(viewModel) {
            loadCurrencies()

            currencies.collectStateIn(viewScope) {
                onSuccess(::handleSuccessResult)
                onFailure(::handleError)
                onLoading(::showProgressBar)
            }
            calculatedValue.collectStateIn(viewScope) {
                onSuccess(::showCalculatedValue)
            }
        }
    }

    private fun initUi() {
        binding.swipeRefresh.refreshes().collectIn(viewScope) {
            with(viewModel) {
                if (currencyEntityReady) loadCurrencies(currencyEntity.base)
                else loadCurrencies()
            }
        }

        binding.btnCalculate.clicks().collectIn(viewScope) {
            with(binding.etEnterCurrency.text) {
                if (isNotBlank()) {
                    val enteredValue = toString().toDouble()
                    val selectedItem = binding.spinnerTargetCurrency.selectedItem
                    val target = viewModel.currencyEntity.rates[selectedItem]
                    viewModel.calculateCurrency(enteredValue, target)
                } else {
                    displayDialog(getString(R.string.please_enter_the_value))
                }
            }
        }

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerInitialCurrency.doOnItemSelected { pos ->
            viewModel.loadCurrencies(spinnerAdapter.getItem(pos))
        }
        binding.spinnerInitialCurrency.adapter = spinnerAdapter
        binding.spinnerTargetCurrency.adapter = spinnerAdapter
    }

    private fun handleSuccessResult(currencyEntity: CurrencyEntity) {
        hideProgressBar()
        hideSwipeRefresh()
        viewModel.currencyEntity = currencyEntity
        spinnerAdapter.addAll(currencyEntity.rates.keys)
        spinnerAdapter.notifyDataSetChanged()
        binding.spinnerInitialCurrency.setSelection(currencyEntity.rates.keys.indexOf(currencyEntity.base))
    }

    private fun showCalculatedValue(calculatedValue: Double) {
        binding.tvConvertedSum.text = calculatedValue.toString()
    }

    private fun showProgressBar() = binding.progressBar.show()

    private fun hideProgressBar() = binding.progressBar.hide()

    private fun hideSwipeRefresh() = binding.swipeRefresh.setRefreshing(false)

    private fun handleError(error: Throwable) {
        when (error) {
            ExceptionOnLoading -> displayDialog(getString(R.string.exception_occured_when_loading))
            ExceptionNetworkConnection -> displayDialog(getString(R.string.no_internet_connection))
            ExceptionCannotLoadData -> displayDialog(getString(R.string.cannot_load_data))
            ExceptionOnCalculate -> displayDialog(getString(R.string.error_when_calculate_data))
        }
    }

    private fun displayDialog(message: String) =
        AlertDialog.Builder(requireContext())
            .setTitle(message)
            .setPositiveButton(getString(R.string.ok)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()

    private fun Spinner.doOnItemSelected(action: (pos: Int) -> Unit) {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) = Unit

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                long: Long
            ) {
                action(pos)
            }

        }
    }
}