package com.icongkhanh.gameworld.model

import com.icongkhanh.gameworld.domain.model.Genre

data class GameDetailGenreUiModel(
    val genre: Genre,
    val onClick: (genre: Genre) -> Unit
)