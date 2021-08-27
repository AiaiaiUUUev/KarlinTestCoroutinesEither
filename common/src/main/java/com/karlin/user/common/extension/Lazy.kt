package com.karlin.user.common.extension

fun <T> lazyUnsafe(action: () -> T) = lazy(LazyThreadSafetyMode.NONE, action)