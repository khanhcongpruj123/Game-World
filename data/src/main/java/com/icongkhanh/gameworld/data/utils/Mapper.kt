package com.icongkhanh.gameworld.data.utils

import com.icongkhanh.gameworld.data.remote.model.*
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.model.Genre
import com.icongkhanh.gameworld.domain.model.PlatformAndRequirement
import com.icongkhanh.gameworld.domain.model.Store

fun GameResponse.mapToDomain(): Game {
    return Game(
        id = this.id?: 0,
        name = this.name?: "",
        description = this.description?: "",
        website = this.website?: "",
        releasedDate = this.released?: "",
        rating = this.rating?: 0f,
        ratingsCount = this.ratings_count?: 0,
        screenShort = this.shortScreenshots?.map { it.url?: "" }?: emptyList(),
        saturatedColor = this.saturated_color?: "#FFFFFF",
        suggestionsCount = this.suggestions_count?: 0,
        clipUrl = this.clip?.clip?: "",
        clipPreviewUrl = this.clip?.preview?: "",
        genre = this.genres?.map { it.mapToDomain() }?: emptyList(),
        imgUrl = this.background_image?: "",
        platforms = this.platforms?.map { it.mapToDomain() }?: emptyList(),
        stores = this.stores?.map { it.mapToDomain() }?: emptyList()
    )
}

fun Stores.mapToDomain(): Store {
    return Store(
        id = this.store?.id?: 0L,
        website = this.url?: "",
        name = this.store?.name?: "",
        imgUrl = this.store?.image_background?: ""
    )
}

fun Platforms.mapToDomain(): PlatformAndRequirement {
    return PlatformAndRequirement(
        platform = this.platform?.mapToDomain(),
        releasedDate = this.released_at?: "",
        requirementMin = this.requirements_en?.recommended?: "",
        requirementRecommended = this.requirements_en?.recommended?: ""
    )
}

fun Platform.mapToDomain(): com.icongkhanh.gameworld.domain.model.Platform {
    return com.icongkhanh.gameworld.domain.model.Platform(
        id = this.id?: 0,
        imgUrl = this.image_background?: "",
        name = this.name?: "",
        gameCount = this.games_count?: 0
    )
}

fun GenreResponse.mapToDomain(): Genre {
    return Genre(
        id = this.id?: 0,
        name = this.name?: "",
        gameCount = this.games_count?: 0,
        imgUrl = this.image_background?: ""
    )
}

fun Game.mapToLocalModel(): com.icongkhanh.gameworld.data.local.entity.Game {
    return com.icongkhanh.gameworld.data.local.entity.Game(
        this.id,
        this.name,
        this.imgUrl,
        this.rating
    )
}

fun com.icongkhanh.gameworld.data.local.entity.Game.mapToDomainModel() = Game(
    id = this.id,
    name = this.name,
    imgUrl = this.imgUrl,
    rating = this.rating,
    isBookmark = true
)