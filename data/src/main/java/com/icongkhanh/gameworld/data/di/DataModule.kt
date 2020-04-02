package com.icongkhanh.gameworld.data.di

import com.icongkhanh.gameworld.data.repository.GameRepositoryImpl
import com.icongkhanh.gameworld.domain.repository.GameRepository
import org.koin.dsl.module

val dataModule = module {
    factory { GameRepositoryImpl(get()) as GameRepository }
}