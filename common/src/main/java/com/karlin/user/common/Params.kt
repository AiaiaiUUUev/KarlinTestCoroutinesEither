package com.karlin.user.common

import androidx.collection.ArrayMap
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class Params {

    @PublishedApi internal val map: ArrayMap<String, Any?> = ArrayMap()

    val keys: Set<String>
        get() = map.keys

    @Suppress("UNCHECKED_CAST")
    @PublishedApi internal inline fun <T, R> getOrElse(key: String, body: (p: T) -> R, elseBody: () -> R): R {
        val index = map.indexOfKey(key)
        return if (index >= 0) body(map.valueAt(index) as T)
        else elseBody()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? = map[key] as? T

    fun <T> require(key: String): T =
        getOrElse<T, T>(key, { it }, { throw MissingRequiredParamsException(key) })

    fun put(key: String, value: Any?) {
        map[key] = value
    }

    infix fun String.of(item: Any?) {
        map[this] = item
    }

    override fun toString(): String = map.entries.joinToString { (key, value) -> "[$key:$value]" }

    companion object {
        val EMPTY = Params()
    }
}
