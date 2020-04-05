package com.icongkhanh.gameworld.data.repository

import android.util.Log
import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.data.remote.GameService
import com.icongkhanh.gameworld.data.utils.mapToDomain
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GameRepositoryImpl(val gameService: GameService): GameRepository {

    override suspend fun getAllGame(): Flow<Result<List<Game>>> = flow {
        val list = withContext(Dispatchers.IO) { gameService.getAllGame() }
    }

    override suspend fun getTopRatingGame(): Flow<Result<List<Game>>> = flow {
        emit(Result.Loading)
        val list = withContext(Dispatchers.IO) { gameService.getAllGame() }
        val res = list.results
        res.sortedByDescending { it.rating }
        emit(Result.Success(res.map { it.mapToDomain() }))
    }

    override suspend fun searchGame(keyword: String): Flow<Result<List<Game>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDetail(id: Long): Flow<Result<Game>> = flow {
        emit(Result.Loading)
        val _res = withContext(Dispatchers.IO) { gameService.getGameDetail(id) }
        Log.d("Repository", "game response: ${_res}")
        val res = _res.mapToDomain()
        emit(Result.Success(res))
    }

}