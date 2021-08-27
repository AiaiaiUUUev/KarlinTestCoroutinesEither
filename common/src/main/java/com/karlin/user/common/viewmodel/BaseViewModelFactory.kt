package com.karlin.user.common.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karlin.user.common.Params

abstract class BaseViewModelFactory<VM : ViewModel>(
) : ViewModelProvider.Factory {

    var params = Params.EMPTY

    abstract fun create(): ViewModel

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T

    fun setArguments(args: Bundle?) {
        args?.let { bundle ->
            params = Params().apply {
                bundle.keySet().forEach {
                    it of bundle.get(it)
                }
            }
        }
    }
}