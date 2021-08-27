package com.karlin.user.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import io.reactivex.Single

@Dao
interface CurrenciesDaoImpl {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(currencies: CurrencyEntity)

    @Query("SELECT * FROM currencies WHERE :base == base")
    suspend fun getCurrencies(base: String): CurrencyEntity?
}