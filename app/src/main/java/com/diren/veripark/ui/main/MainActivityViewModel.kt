package com.diren.veripark.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diren.veripark.R
import com.diren.veripark.core.BaseViewModel
import com.diren.veripark.data.Event
import com.diren.veripark.ui.main.MainActivityEvent


class MainActivityViewModel() : BaseViewModel()
{


    private val _event = MutableLiveData<Event<MainActivityEvent>>()
    val event: LiveData<Event<MainActivityEvent>> = _event
    val test = MutableLiveData<String>()

    fun testClick(){
         _event.postValue(Event(MainActivityEvent.ClickOnButton))
    }

}
