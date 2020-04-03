package com.icongkhanh.gameworld.data.utils

import com.icongkhanh.gameworld.data.remote.model.GameResponse
import com.icongkhanh.gameworld.data.remote.model.GenreResponse
import com.icongkhanh.gameworld.data.remote.model.Platform
import com.icongkhanh.gameworld.data.remote.model.Platforms
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.model.Genre
import com.icongkhanh.gameworld.domain.model.PlatformAndRequirement

fun GameResponse.mapToDomain(): Game {
    return Game(
        id = this.id?: 0,
        name = this.name?: "",
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
        platforms = this.platforms?.map { it.mapToDomain() }?: emptyList()
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