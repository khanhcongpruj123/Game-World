package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface GetTopRatingGameUsecase {

    operator suspend fun invoke(page: Long): Flow<Result<List<Game>>>
}

class GetTopRatingGameUsecaseImpl(val repository: GameRepository): GetTopRatingGameUsecase {

    override suspend fun invoke(page: Long): Flow<Result<List<Game>>> {
        return repository.getTopRatingGame(page)
    }

}