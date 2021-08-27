package com.karlin.user.common.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun Fragment.navigateUp() {
    val navController = findNavController()
    val current = navController.currentDestination ?: run {
        navController.navigateUp()
        return
    }

    var destId = current.id
    var parent = current.parent
    while (parent != null) {
        if (parent.startDestination != destId) {
            if (!navController.popBackStack(parent.startDestination, false)) {
                navController.navigateUp()
            }
            return
        }
        destId = parent.id
        parent = parent.parent
    }

    navController.navigateUp()
}