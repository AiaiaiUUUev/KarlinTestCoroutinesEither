package com.karlin.user.common.flows

import com.karlin.user.common.viewmodel.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("FunctionName")
fun <T> MutableDataStateFlow() = MutableStateFlow<DataState<T>>(DataState.loading())

inline fun <T> Flow<T>.collectIn(scope: CoroutineScope, crossinline action: suspend (T) -> Unit) {
    scope.launch {
        this@collectIn.collect(action)
    }
}

suspend inline fun <T> Flow<DataState<T>>.collectState(crossinline action: suspend DataState<T>.() -> Unit) {
    this@collectState.collect(action)
}

inline fun <T> Flow<DataState<T>>.collectStateIn(scope: CoroutineScope, crossinline action: suspend DataState<T>.() -> Unit) {
    scope.launch {
        collectState(action)
    }
}