package com.icongkhanh.gameworld.data.remote

import com.icongkhanh.gameworld.data.remote.di.HOST
import com.icongkhanh.gameworld.data.remote.di.KEY
import com.icongkhanh.gameworld.data.remote.model.GameResponse
import com.icongkhanh.gameworld.data.remote.model.GetAllGameResponse
import com.icongkhanh.gameworld.domain.model.Game
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @Headers(
        "x-rapidapi-host: ${HOST}",
        "x-rapidapi-key: ${KEY}"
    )
    @GET("/games")
    suspend fun getAllGame(@Query("page") page: Long = 1): GetAllGameResponse

    @Headers(
        "x-rapidapi-host: ${HOST}",
        "x-rapidapi-key: ${KEY}"
    )
    @GET("/games/{game_pk}")
    suspend fun getGameDetail(@Path("game_pk") gameId: Long): GameResponse
}