package com.example.dispatchers.di

import com.example.dispatchers.DispatchersFactory
import com.example.dispatchers.DispatchersFactoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DispatchersModule {
    @Binds
    @Singleton
    fun provide(factoryImpl: DispatchersFactoryImpl): DispatchersFactory
}