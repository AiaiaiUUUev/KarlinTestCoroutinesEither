package com.karlin.user.testsberbankkarlin

import android.app.Application
import android.content.Context
import com.karlin.user.common.FeatureDependenciesProvider
import com.karlin.user.common.HasFeatureDependencies
import com.karlin.user.testsberbankkarlin.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.testsberbankkarlin
 */

class App : Application(), HasFeatureDependencies {

    @Inject
    override lateinit var dependencies: FeatureDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAppComponent(applicationContext)
    }

    private fun initAppComponent(applicationContext: Context) {
        DaggerAppComponent.builder()
            .applicationContext(applicationContext)
            .build()
            .inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}