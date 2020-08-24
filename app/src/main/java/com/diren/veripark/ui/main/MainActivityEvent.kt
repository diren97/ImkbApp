package com.diren.veripark.ui.main

import android.graphics.drawable.Drawable

sealed class MainActivityEvent {
    object ClickOnButton : MainActivityEvent()
    data class ClickText(val data: String) : MainActivityEvent()

    }