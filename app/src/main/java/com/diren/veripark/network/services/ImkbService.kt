package com.diren.veripark.network.services

import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.network.responses.HandShakeResponse
import com.diren.veripark.network.responses.StockRequest
import com.diren.veripark.network.responses.Stocks

import retrofit2.http.*


interface ImkbService {

    @POST("/api/handshake/start")
    suspend fun handShake(
         @Body request:DeviceInfo
    ): HandShakeResponse


    @POST("/api/stocks/list")
    suspend fun Stocks(
        @Body period:StockRequest
    ): Stocks
 }
