package com.example.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatchersFactoryImpl @Inject constructor() : DispatchersFactory {

    override fun default(): CoroutineDispatcher = Dispatchers.Default

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun main(): CoroutineDispatcher = Dispatchers.Main
}