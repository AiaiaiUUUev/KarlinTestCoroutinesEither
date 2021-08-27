package com.karlin.user.feature_currencyconverter.di

import com.karlin.user.feature_currencyconverter.data.CurrenciesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.di
 */

@Module
interface CurrencyRepositoryModule {

    @Binds
    @Singleton
    fun provideCurrenciesRepository(repo: CurrenciesRepository.Impl): CurrenciesRepository
}