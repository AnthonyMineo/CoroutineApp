package com.example.coroutineapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class InternetUtils {
    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            return isWifiAvailable(context) || isMobileDataAvailable(context)
        }

        private fun isWifiAvailable(context: Context): Boolean {
            val cM: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cM.activeNetworkInfo
            return if(activeNetwork != null && activeNetwork.isConnectedOrConnecting){
                activeNetwork.type == ConnectivityManager.TYPE_WIFI
            } else {
                false
            }
        }

        private fun isMobileDataAvailable(context: Context): Boolean{
            val cM: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cM.activeNetworkInfo
            return if(activeNetwork != null && activeNetwork.isConnectedOrConnecting){
                activeNetwork.type == ConnectivityManager.TYPE_MOBILE
            } else {
                false
            }
        }
    }
}