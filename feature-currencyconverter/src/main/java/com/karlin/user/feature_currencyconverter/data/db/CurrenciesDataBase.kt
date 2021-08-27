package com.karlin.user.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity

/**
// Created by User on 30.06.2019.
// PackageName com.karlin.user.data.db
 */

fun createCurrenciesDao(context: Context): CurrenciesDaoImpl {
    return Room.databaseBuilder(context, CurrenciesDataBase::class.java, "currencies_db")
            .build().moviesDao()
}

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
internal abstract class CurrenciesDataBase : RoomDatabase() {
    abstract fun moviesDao(): CurrenciesDaoImpl
}