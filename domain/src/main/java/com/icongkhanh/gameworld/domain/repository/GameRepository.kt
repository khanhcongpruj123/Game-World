package com.icongkhanh.gameworld.domain.repository

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getAllGame() : Flow<Result<List<Game>>>
    suspend fun getTopRatingGame(page: Long = 1): Flow<Result<List<Game>>>
    suspend fun searchGame(keyword: String): Flow<Result<List<Game>>>
    suspend fun getGameDetail(id: Long): Flow<Result<Game>>
    suspend fun getGameOfGenre(genreId: Long): Flow<Result<List<Game>>>
    suspend fun bookmarkGame(id: Long): Flow<Result<Boolean>>
    suspend fun unBookmarkGame(id: Long): Flow<Result<Boolean>>
    suspend fun getAllBookmarkGame(): Flow<Result<List<Game>>>
}