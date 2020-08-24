package com.diren.veripark.core

import android.app.Application
import com.diren.veripark.BuildConfig
import com.diren.veripark.di.networkModule
import com.diren.veripark.di.repositoryModule
import com.diren.veripark.di.utilsModule
import com.diren.veripark.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ImkbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@ImkbApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    utilsModule
                )
            )
        }
    }
}
