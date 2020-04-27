package com.icongkhanh.gameworld.viewmodel.di

import com.icongkhanh.gameworld.viewmodel.GameDetailFragmentViewModel
import com.icongkhanh.gameworld.viewmodel.HomeFragmentViewModel
import com.icongkhanh.gameworld.viewmodel.SearchFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeFragmentViewModel(get(), get()) }
    viewModel { GameDetailFragmentViewModel(get(), get()) }
    viewModel { SearchFragmentViewModel(get()) }
}