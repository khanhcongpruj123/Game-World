package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface GetGameDetailUsecase {

    suspend operator fun  invoke(id: Long): Flow<Result<Game>>
}

class GetGameDetailUsecaseImpl(val repository: GameRepository): GetGameDetailUsecase {

    override suspend fun invoke(id: Long): Flow<Result<Game>> {
        return repository.getGameDetail(id)
    }

}