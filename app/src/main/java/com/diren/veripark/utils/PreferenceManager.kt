package com.diren.veripark.utils

import android.annotation.SuppressLint
import android.content.Context
import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.ext.get
import com.diren.veripark.ext.set
import com.diren.veripark.network.responses.HandShakeResponse
import com.diren.veripark.network.responses.Stocks


class PreferenceManager(context: Context) {

    companion object {
        private const val PREFS = "prefs"
        private const val TOKEN = "token"
        private const val HANDSHAKE = "handshake"
        private const val STOCKS = "stocks"
        private const val UUID = "uuid"
         private const val DEVICE_INFO = "device_info"
         private const val AES_KEY = "aes_key"
        private const val AES_IV = "aes_iv"
      }

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun clearAll() {
        with(prefs.edit()) {
            remove(UUID)
            remove(TOKEN)
         }.apply()
    }
    /**
     * Device unique id
     */
    var uuid: String
        get() = prefs.get(UUID)!!
        set(value) { prefs.set(UUID, value) }

    /**
     * Device Info
     */
    var deviceInfo: DeviceInfo?
        get() = prefs.get(DEVICE_INFO)
        set(value) {
            prefs.set(DEVICE_INFO, value)
        }
    /**
     * HandShake
     */
    var handShake: HandShakeResponse?
        get() = prefs.get(HANDSHAKE)
        set(value) {
            prefs.set(HANDSHAKE, value)
        }

    /**
     * AES KEY
     */
    var aesKey: String?
        get() = prefs.get(AES_KEY)
        set(value) {
            prefs.set(AES_KEY, value)
        }

    /**
     * AES_IV
     */
    var aesIV: String?
        get() = prefs.get(AES_IV)
        set(value) {
            prefs.set(AES_IV, value)
        }
    /**
     * Stock
     */
    var stocks: Stocks?
        get() = prefs.get(STOCKS)
        set(value) {
            prefs.set(STOCKS, value)
        }
}

