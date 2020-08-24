package com.diren.veripark.network.repositories

import androidx.annotation.VisibleForTesting
import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.network.responses.HandShakeResponse
import com.diren.veripark.network.responses.StockRequest
import com.diren.veripark.network.responses.Stocks
import com.diren.veripark.network.services.ImkbService
import com.diren.veripark.utils.PreferenceManager
import  com.diren.veripark.network.utils.Result

interface ImkbRepository {
    suspend fun handShake(request: DeviceInfo): Result<HandShakeResponse>
    suspend fun stock(period: StockRequest): Result<Stocks>
}

class DefaultImkbRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: ImkbService,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val preferenceManager: PreferenceManager
) : ImkbRepository {
    override suspend fun handShake(request: DeviceInfo): Result<HandShakeResponse> {
        return try {
            Result.Success(service.handShake(request))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
    override  suspend fun stock(period: StockRequest): Result<Stocks> {
        return try {
            Result.Success(service.Stocks(period))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}
