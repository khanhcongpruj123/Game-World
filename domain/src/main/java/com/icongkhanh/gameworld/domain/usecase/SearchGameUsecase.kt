package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface SearchGameUsecase {

    suspend operator fun invoke(keyword: String): Flow<Result<List<Game>>>
}

class SearchGameUsecaseImpl(val repository: GameRepository): SearchGameUsecase {

    override suspend fun invoke(keyword: String): Flow<Result<List<Game>>> {
       return repository.searchGame(keyword)
    }

}