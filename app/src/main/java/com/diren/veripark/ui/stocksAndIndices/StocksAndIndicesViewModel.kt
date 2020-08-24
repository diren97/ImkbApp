package com.diren.veripark.ui.stocksAndIndices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diren.veripark.core.BaseViewModel
import com.diren.veripark.data.Event
import com.diren.veripark.network.repositories.ImkbRepository
import com.diren.veripark.network.responses.StockRequest
import com.diren.veripark.network.utils.Result
import com.diren.veripark.utils.PreferenceManager
import kotlinx.coroutines.launch

class StocksAndIndicesViewModel(
    val repository: ImkbRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {
    private val _event = MutableLiveData<Event<StocksAndIndicesViewEvent>>()
    val event: LiveData<Event<StocksAndIndicesViewEvent>> = _event
/*
    fun getStocks()=
        viewModelScope.launch {
            //val response = preferenceManager.aesKey?.let { repository.stock() }?.let { repository.stock(it) }//PERIOD ISIMLERINI DENE HERSEY
            when (response) {
                is Result.Success -> {
                    _event.postValue(Event(StocksAndIndicesViewEvent.ClickText(response.data)))
                }
                is Result.Error -> {

                }
            }
        }*/
}