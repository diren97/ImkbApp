package com.diren.veripark.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diren.veripark.core.BaseViewModel
import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.data.Event
import com.diren.veripark.network.repositories.ImkbRepository
import com.diren.veripark.network.utils.Result
import com.diren.veripark.ui.splash.SplashViewEvent
import com.diren.veripark.utils.PreferenceManager
import kotlinx.coroutines.launch


class SplashViewModel(
    //imkb içindeki metotlara erisebilmek icin
    val imkbRepository: ImkbRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {


    private val _event = MutableLiveData<Event<SplashViewEvent>>()
    val event: LiveData<Event<SplashViewEvent>> = _event
//livedata aktif olarak değişen verileri tutan evente ekleyen kısım
    //mutablelive data ise setvalue ve getvalue metotlarını kullabilmemizi sağlar
    val test = MutableLiveData<String>()

    fun handShake(deviceInfo: DeviceInfo) = viewModelScope.launch {
        val response = imkbRepository.handShake(deviceInfo)
        when (response) {
            is Result.Success -> {
                preferenceManager.handShake = response.data
                _event.postValue(Event(SplashViewEvent.HandShakeDecoder(response.data)))

            }
            is Result.Error -> {
                handleException(response.exception)
            }
        }
    }
}
