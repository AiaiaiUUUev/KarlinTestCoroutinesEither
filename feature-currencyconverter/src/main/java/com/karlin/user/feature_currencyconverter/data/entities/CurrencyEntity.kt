package com.karlin.user.feature_currencyconverter.data.entities

import androidx.collection.ArrayMap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.karlin.user.feature_currencyconverter.data.db.MapConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "currencies")
@TypeConverters(MapConverter::class)
@JsonClass(generateAdapter = true)
data class CurrencyEntity(
    @PrimaryKey @Json(name = "base")
    val base: String,
    @Json(name = "rates")
    val rates: Map<String, Double>
) {
    companion object {
         val EMPTY_CURRENCY_ENTITY = CurrencyEntity("RUB", emptyMap())
    }
}