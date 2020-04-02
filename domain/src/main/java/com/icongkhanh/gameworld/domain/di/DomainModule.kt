package com.icongkhanh.gameworld.domain.di

import com.icongkhanh.gameworld.domain.repository.GameRepository
import com.icongkhanh.gameworld.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllGameUsecaseImpl(get()) as GetAllGameUsecase }
    factory { GetGameDetailUsecaseImpl(get()) as GetGameDetailUsecase }
    factory { GetTopRatingGameUsecaseImpl(get()) as GetTopRatingGameUsecase }
    factory { SearchGameUsecaseImpl(get()) as SearchGameUsecase }
}