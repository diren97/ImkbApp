package com.diren.veripark.network.utils

import com.diren.veripark.utils.PreferenceManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class Authenticator(private val preferenceManager: PreferenceManager) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder()
            .addHeader("Authorization", "${preferenceManager.handShake?.authorization}")
            .build()
    }

 }
