package com.diren.veripark.di

import com.diren.veripark.utils.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val utilsModule = module {
    single { PreferenceManager(androidApplication()) }
}