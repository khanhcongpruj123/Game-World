package com.icongkhanh.gameworld.domain.usecase

import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface GetGameOfGenreUsecase {
    suspend operator fun invoke(genreId: Long): Flow<Result<List<Game>>>
}

class GetGameOfGenreUsecaseImpl(val repository: GameRepository) : GetGameOfGenreUsecase {
    override suspend fun invoke(genreId: Long): Flow<Result<List<Game>>> {
        return repository.getGameOfGenre(genreId)
    }

}