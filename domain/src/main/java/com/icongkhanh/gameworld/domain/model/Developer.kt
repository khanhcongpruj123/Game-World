package com.icongkhanh.gameworld.domain.model

data class Developer (
    val id: Long = 0,
    val name: String = "",
    val gameCount: Long = 0,
    val imgUrl: String = "",
    val listGame: List<Game> = emptyList()
)
