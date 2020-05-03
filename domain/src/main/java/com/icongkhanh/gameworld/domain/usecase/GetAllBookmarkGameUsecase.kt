package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface GetAllBookmarkGameUsecase {
    suspend operator fun invoke(): Flow<Result<List<Game>>>
}

class GetAllBookmarkGameUsecaseImpl(val repository: GameRepository) : GetAllBookmarkGameUsecase {

    override suspend fun invoke(): Flow<Result<List<Game>>> {
        return repository.getAllBookmarkGame()
    }

}