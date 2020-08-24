package com.diren.veripark.di

import com.diren.veripark.network.utils.NetworkClient
import com.diren.veripark.utils.PreferenceManager
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single { NetworkClient.provideClient(get<PreferenceManager>()) }
    single { NetworkClient.provideImkbService(get()) }

}
