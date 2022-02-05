package sourabh.pal.cricket.common.data.api

import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
class ConnectionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var _isConnected = false
    val isConnected
        get() = _isConnected

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected = true
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            _isConnected = false
            super.onLost(network)
        }
    }

    init {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}