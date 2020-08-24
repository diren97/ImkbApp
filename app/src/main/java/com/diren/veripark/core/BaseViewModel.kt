package com.diren.veripark.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonSyntaxException
import com.diren.veripark.data.Event
import java.net.UnknownHostException
import retrofit2.HttpException



abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _baseEvent = MutableLiveData<Event<BaseViewEvent>>()
    val baseEvent: LiveData<Event<BaseViewEvent>> = _baseEvent

    fun setLoading(loading: Boolean) = _loading.postValue(loading)

    private fun forceLogout() = _baseEvent.postValue(Event(BaseViewEvent.ForceLogout))

    private fun showCommonNetworkError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCommonNetworkError))

    private fun showConnectivityError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowConnectivityError))

    private fun showCustomError(message: String) =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCustomError(message)))

    open fun handleException(e: Exception) {
        when (e) {
            is HttpException -> {
                when (e.code()) {

                 }
            }

            is JsonSyntaxException -> showCommonNetworkError()

            is UnknownHostException -> showConnectivityError()
        }
    }
}
