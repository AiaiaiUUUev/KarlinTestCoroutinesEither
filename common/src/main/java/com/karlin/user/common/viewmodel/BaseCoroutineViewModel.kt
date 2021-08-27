package com.karlin.user.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseCoroutineViewModel : ViewModel() {

    var component : Any? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable)}

    @CallSuper
    override fun onCleared() {
        component = null
        super.onCleared()
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler, block = block)
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun <T> launchCatching(
        stateFlow: MutableStateFlow<DataState<T>>,
        block: suspend CoroutineScope.() -> T
    ) {
        stateFlow.value = DataState.loading()
        viewModelScope.launch(exceptionHandler) {
            stateFlow.value = runCatching { block() }.fold(
                onSuccess = { data -> DataState.success(data) },
                onFailure = { e ->
                    Timber.e(e)
                    DataState.failure(e)
                }
            )
        }
    }
}