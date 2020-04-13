package com.icongkhanh.gameworld.data.remote.di

import com.icongkhanh.gameworld.data.remote.GameService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://rawg-video-games-database.p.rapidapi.com"
const val HOST = "rawg-video-games-database.p.rapidapi.com"
const val KEY = "394b2cdf61msh281b3202b01fabcp1730ddjsn09d2946bef9b"

val remoteModule = module {

    single {

        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create(GameService::class.java) }
}