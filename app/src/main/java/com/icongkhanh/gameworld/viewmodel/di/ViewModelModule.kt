package com.icongkhanh.gameworld.viewmodel.di

import com.icongkhanh.gameworld.viewmodel.HomeFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeFragmentViewModel(get()) }
}