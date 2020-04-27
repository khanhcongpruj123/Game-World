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
        val page = (1L..20).random()
        val list = withContext(Dispatchers.IO) { gameService.getAllGame(page) }
    }

    override suspend fun getTopRatingGame(page: Long): Flow<Result<List<Game>>> = flow {
        emit(Result.Loading)
        try {
            val list = withContext(Dispatchers.IO) { gameService.getAllGame(page) }
            val res = list.results
            res.sortedByDescending { it.rating }
            emit(Result.Success(res.map { it.mapToDomain() }))
        } catch (ex: Exception) {
            emit(Result.Error(ex))
        }
    }

    override suspend fun searchGame(keyword: String): Flow<Result<List<Game>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDetail(id: Long): Flow<Result<Game>> = flow {
        emit(Result.Loading)
        try {
            val res = withContext(Dispatchers.IO) {
                val _res = gameService.getGameDetail(id)
                Log.d("Repository", "${_res.stores?.get(0)}")
                _res
            }.mapToDomain()
            Log.d("Repository", "${res.stores?.get(0)}")
            emit(Result.Success(res))
        } catch (ex: Exception) {
            emit(Result.Error(ex))
        }
    }

    override suspend fun getGameOfGenre(genreId: Long): Flow<Result<List<Game>>> = flow {
        emit(Result.Loading)
        try {
            val res = withContext(Dispatchers.IO) {
                val page = (1..20).random()
                val _res = gameService.getAllGame(page.toLong())
                _res
            }.results.map { it.mapToDomain() }.filter {
                it.genre.map { it.id }.contains(genreId)
            }
            emit(Result.Success(res))
        } catch (ex: Exception) {
            emit(Result.Error(ex))
        }
    }

}