package com.diren.veripark.ui.splash

import com.diren.veripark.network.responses.HandShakeResponse


sealed class SplashViewEvent {
    object ClickOnButton : SplashViewEvent()
    object NavigateToMain : SplashViewEvent()
    data class ClickText(val data: String) : SplashViewEvent()
    data class HandShakeDecoder(val data: HandShakeResponse) : SplashViewEvent()


}