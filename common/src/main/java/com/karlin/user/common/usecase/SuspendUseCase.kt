package com.karlin.user.common.usecase

import com.example.dispatchers.DispatchersFactory
import com.karlin.user.common.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<T>(
    protected val dispatchersFactory: DispatchersFactory
) {

    abstract suspend fun sourceImpl(params: Params = Params.EMPTY): T

//    suspend inline fun sourceImpl(initializer: Params.() -> Unit): T =
//        sourceImpl(Params().apply(initializer))
//
    suspend fun source(params: Params = Params.EMPTY): T =
        withContext(dispatchersFactory.io()) { sourceImpl(params) }
//
    suspend inline fun source(initializer: Params.() -> Unit): T =
        source(Params().apply(initializer))
//
//    suspend fun source(dispatcher: CoroutineDispatcher, params: Params = Params.EMPTY): T =
//        withContext(dispatcher) { sourceImpl(params) }
//
//    suspend inline fun source(dispatcher: CoroutineDispatcher, initializer: Params.() -> Unit): T =
//        source(dispatcher, Params().apply(initializer))
}