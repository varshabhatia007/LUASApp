package com.example.luasapp.common
// https://gist.github.com/PasanBhanu/730a32a9eeb180ec2950c172d54bb06a
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

interface NetworkStatusListener {
    fun isConnectionAvailable(isConnected: Boolean)
}

class CheckNetwork(
    private val context: Context,
    private val listener: NetworkStatusListener?
) {

    init {
        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()

            connectivityManager.registerNetworkCallback(
                builder.build(),
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        listener?.isConnectionAvailable(true)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        listener?.isConnectionAvailable(false)
                    }
                })
        } catch (e: Exception) {
            listener?.isConnectionAvailable(false)
        }
    }
}