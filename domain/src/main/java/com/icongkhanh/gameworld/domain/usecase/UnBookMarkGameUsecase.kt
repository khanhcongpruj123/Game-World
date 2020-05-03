package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface UnBookMarkGameUsecase {
    suspend operator fun invoke(id: Long): Flow<Result<Boolean>>
}

class UnBookMarkUsecaseImpl(val repo: GameRepository) : UnBookMarkGameUsecase {

    override suspend fun invoke(id: Long): Flow<Result<Boolean>> {
        return repo.unBookmarkGame(id)
    }

}