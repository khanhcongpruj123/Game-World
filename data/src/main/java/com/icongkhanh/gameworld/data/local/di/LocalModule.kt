package com.icongkhanh.gameworld.data.local.di

import com.icongkhanh.gameworld.data.local.database.GameDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { GameDatabase.getInstance(androidContext()) }
    factory { get<GameDatabase>().gameDao() }
}