package com.diren.veripark.network.utils

import com.google.gson.GsonBuilder
import com.diren.veripark.BuildConfig
import com.diren.veripark.network.services.ImkbService
import com.diren.veripark.network.utils.NetworkClient.APP_BASE_URL
import com.diren.veripark.utils.PreferenceManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object NetworkClient {

    /** Timeout values **/
    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 120L
    private const val RECONNECT_INTERVAL: Long = 10 * 1000

    /** Base Endpoint URLs **/
    private const val APP_BASE_URL: String = BuildConfig.API_IMKB_BASE_URL

    /** Provides Retrofit Interface **/
    private fun <T> provideService(
        client: OkHttpClient,
        baseUrl: String,
        clazz: Class<T>,
        scalars: Boolean = false
    ): T {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        val gson = gsonBuilder.create()

        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)

        if (scalars) {
            builder.addConverterFactory(ScalarsConverterFactory.create())
        } else {
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        }

        builder.addConverterFactory(EnumConverterFactory())

        return builder.build().create(clazz)
    }

    /** Provides special OkHttp client for ONLY refresh token request **/
    fun provideClient(
        preferenceManager: PreferenceManager
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Authorization", "${preferenceManager.handShake?.authorization?:""}")
                chain.proceed(builder.build())
            }.addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    fun provideImkbService(client: OkHttpClient) =
        provideService(
            client,
            APP_BASE_URL,
            ImkbService::class.java
        )
}
