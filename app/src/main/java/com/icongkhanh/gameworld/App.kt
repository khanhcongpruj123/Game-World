package com.icongkhanh.gameworld

import android.app.Application
import com.icongkhanh.gameworld.data.di.dataModule
import com.icongkhanh.gameworld.data.local.di.localModule
import com.icongkhanh.gameworld.data.remote.di.remoteModule
import com.icongkhanh.gameworld.domain.di.domainModule
import com.icongkhanh.gameworld.viewmodel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    domainModule,
                    remoteModule,
                    dataModule,
                    localModule,
                    viewModelModule
                )
            )
        }
    }
}