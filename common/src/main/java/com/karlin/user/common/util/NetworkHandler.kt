package com.karlin.user.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected
        get():Boolean = run {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    return@run when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            } else {
                return@run try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    activeNetworkInfo != null && activeNetworkInfo.isConnected
                } catch (e: Exception) {
                    false
                }
            }
            false
        }
}