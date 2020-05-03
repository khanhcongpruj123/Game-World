package com.icongkhanh.gameworld.domain.di

import com.icongkhanh.gameworld.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllGameUsecaseImpl(get()) as GetAllGameUsecase }
    factory { GetGameDetailUsecaseImpl(get()) as GetGameDetailUsecase }
    factory { GetTopRatingGameUsecaseImpl(get()) as GetTopRatingGameUsecase }
    factory { SearchGameUsecaseImpl(get()) as SearchGameUsecase }
    factory { GetGameOfGenreUsecaseImpl(get()) as GetGameOfGenreUsecase }
    factory { BookMarkGameUsecaseImpl(get()) as BookmarkGameUsecase }
    factory { UnBookMarkUsecaseImpl(get()) as UnBookMarkGameUsecase }
    factory { GetAllBookmarkGameUsecaseImpl(get()) as GetAllBookmarkGameUsecase }
}