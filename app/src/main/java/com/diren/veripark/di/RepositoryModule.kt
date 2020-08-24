package com.diren.veripark.di

import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.network.repositories.DefaultImkbRepository
 import com.diren.veripark.network.repositories.ImkbRepository
 import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<ImkbRepository> { DefaultImkbRepository(get(), get()) }

}
