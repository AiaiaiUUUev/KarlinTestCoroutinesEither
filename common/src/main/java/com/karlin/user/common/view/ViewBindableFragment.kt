package com.karlin.user.common.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * Маркер-интерфейс для использования [viewBindings] во фрагментах
 */
interface ViewBindableFragment : LifecycleOwner {

    val viewLifecycleOwnerDelegate: LifecycleOwner

    val viewLifecycleOwnerLiveDataDelegate: LiveData<LifecycleOwner>

    fun onDestroyBinding() {
    }
}