package com.karlin.user.testsberbankkarlin.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.dispatchers.di.DispatchersModule
import com.karlin.user.common.FeatureDependencies
import com.karlin.user.common.FeatureDependenciesKey
import com.karlin.user.common.FeatureDependenciesProvider
import com.karlin.user.feature_currencyconverter.di.FeatureCurrencyConverterDepedencies
import com.karlin.user.testsberbankkarlin.App
import com.karlin.user.testsberbankkarlin.presentation.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.testsberbankkarlin.di
 */

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DispatchersModule::class,
        FeatureDependenciesModule::class
    ]
)
interface AppComponent : FeatureCurrencyConverterDepedencies {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }
    fun inject(app: App)
}

@Module
abstract class FeatureDependenciesModule {
    @Binds
    @IntoMap
    @FeatureDependenciesKey(FeatureCurrencyConverterDepedencies::class)
    abstract fun findFeatureCurrencyConverterDepedencies(component: AppComponent): FeatureDependencies
}