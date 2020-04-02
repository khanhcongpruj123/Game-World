package com.icongkhanh.gameworld.domain.model

data class Publisher (
    val id: Long,
    val name: Long,
    val description: String,
    val gameCount: Long,
    val listGame: List<Game>
)
