package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface BookmarkGameUsecase {

    suspend operator fun invoke(id: Long): Flow<Result<Boolean>>
}

class BookMarkGameUsecaseImpl(val repo: GameRepository) : BookmarkGameUsecase {

    override suspend fun invoke(id: Long): Flow<Result<Boolean>> {
        return repo.bookmarkGame(id)
    }
}