package com.icongkhanh.gameworld.data.remote.model

import com.icongkhanh.gameworld.domain.model.Game

data class SearchGame(
    val percent: Double,
    val game: Game
)