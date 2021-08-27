package com.karlin.user.common.view

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: ViewBindableFragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveDataDelegate.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            fragment.onDestroyBinding()
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwnerDelegate.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

fun <T : ViewBinding> ViewBindableFragment.viewBindings(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <reified VB : ViewBinding> FragmentActivity.viewBindings(
    crossinline inflate: (LayoutInflater) -> VB,
    setContentView: Boolean = true
): Lazy<VB> =
    object : Lazy<VB> {
        private var binding: VB? = null

        override fun isInitialized() = binding != null

        override val value: VB
            get() = binding ?: inflate.invoke(layoutInflater)
                .also {
                    if (setContentView) {
                        setContentView(it.root)
                    }
                    binding = it
                }
    }