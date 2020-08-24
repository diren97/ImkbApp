package com.diren.veripark.di

import com.diren.veripark.main.MainActivityViewModel
import com.diren.veripark.main.SplashViewModel
import com.diren.veripark.ui.stocksAndIndices.StocksAndIndicesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val viewModelModule = module {

    // PreLogin
    viewModel { MainActivityViewModel()}
    viewModel { SplashViewModel(get(),get()) }
    viewModel { StocksAndIndicesViewModel(get(),get()) }
}
