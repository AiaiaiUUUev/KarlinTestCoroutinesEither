package com.karlin.user.common

import androidx.fragment.app.Fragment
import dagger.MapKey
import dagger.Module
import dagger.multibindings.Multibinds
import kotlin.reflect.KClass

interface FeatureDependencies

inline fun <reified T : FeatureDependencies> Fragment.findFeatureDependencies(): T {
    return findFeatureDependenciesProvider()[T::class.java] as T
}

typealias FeatureDependenciesProvider = Map<Class<out FeatureDependencies>, @JvmSuppressWildcards FeatureDependencies>

interface HasFeatureDependencies {
    val dependencies: FeatureDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class FeatureDependenciesKey(val value: KClass<out FeatureDependencies>)

@Module
abstract class DummyFeatureDependenciesModule private constructor() {
    @Multibinds
    abstract fun componentDependencies(): FeatureDependenciesProvider
}

fun Fragment.findFeatureDependenciesProvider(): FeatureDependenciesProvider {
    var current: Fragment? = parentFragment
    while (current !is HasFeatureDependencies?) {
        current = current?.parentFragment
    }

    val hasDaggerProviders = current ?: when {
        activity is HasFeatureDependencies -> activity as HasFeatureDependencies
        activity?.application is HasFeatureDependencies -> activity?.application as HasFeatureDependencies
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }
    return hasDaggerProviders.dependencies
}