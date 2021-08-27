package com.karlin.user.feature_currencyconverter.data.db

import androidx.collection.ArrayMap
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karlin.user.common.extension.lazyUnsafe
import java.lang.reflect.Type
import java.util.*

internal class MapConverter {
    val gson by lazyUnsafe { Gson() }

    @TypeConverter
    fun fromMap(map: Map<String?, Double?>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromString(serializedMap: String?): Map<String, Double> {
        val type: Type = object : TypeToken<Map<String?, Double?>?>() {}.type
        return gson.fromJson(serializedMap, type)
    }
}