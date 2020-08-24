package com.diren.veripark.ui.stocksAndIndices

import com.diren.veripark.network.responses.HandShakeResponse
import com.diren.veripark.network.responses.Stocks
import com.diren.veripark.ui.splash.SplashViewEvent


sealed class StocksAndIndicesViewEvent {
    object ClickOnButton : StocksAndIndicesViewEvent()
    data class ClickText(val data: Stocks) : StocksAndIndicesViewEvent()
    data class StockAES(val data: HandShakeResponse) : StocksAndIndicesViewEvent()
}