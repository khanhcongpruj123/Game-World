package com.icongkhanh.gameworld.domain.model

data class Genre (
    val id: Long = 0,
    val name: String = "",
    val imgUrl: String = "",
    val description: String = "",
    val gameCount: Long = 0,
    val listGame: List<Game> = emptyList()
)
