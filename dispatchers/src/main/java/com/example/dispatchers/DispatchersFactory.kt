package com.example.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersFactory {

    fun default(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}