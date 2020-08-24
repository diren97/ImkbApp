package com.diren.veripark.core


sealed class BaseViewEvent {

    object ForceLogout : BaseViewEvent()

    object ShowCommonNetworkError : BaseViewEvent()

    object ShowConnectivityError : BaseViewEvent()

    data class ShowCustomError(val message: String) : BaseViewEvent()
}
