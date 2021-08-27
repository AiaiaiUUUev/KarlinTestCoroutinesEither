package com.karlin.user.common.view

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment(
    @LayoutRes layoutId: Int = 0
) : Fragment(layoutId), ViewBindableFragment {

    override val viewLifecycleOwnerDelegate: LifecycleOwner
        get() = viewLifecycleOwner

    override val viewLifecycleOwnerLiveDataDelegate: LiveData<LifecycleOwner>
        get() = viewLifecycleOwnerLiveData

    //region extensions
    val viewScope: LifecycleCoroutineScope
        get() = viewLifecycleOwner.lifecycleScope

//endregion
}