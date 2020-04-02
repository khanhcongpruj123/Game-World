package com.icongkhanh.gameworld.domain.model

data class Store (
    val id: Long = 0,
    val name: String = "",
    val website: String = "",
    val imgUrl: String = "",
    val gameCount: Long = 0L,
    val listGame: List<Game> = emptyList()
)
