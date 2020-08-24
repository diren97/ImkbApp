package com.diren.veripark.network.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class Token(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long
) {

    private var expirationInMillis: Long = 0

    fun calculateExpiration() {
        expirationInMillis = Date().time + expiresIn * 1000
    }

    fun isExpired(): Boolean {
        return Date().time > expirationInMillis
    }
}
