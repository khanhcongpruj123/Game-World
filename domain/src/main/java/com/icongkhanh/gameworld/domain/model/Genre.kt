package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class Genre (
    val id: Long = 0,
    val name: String = "",
    val imgUrl: String = "",
    val description: String = "",
    val gameCount: Long = 0,
    val listGame: List<Game> = emptyList()
): Serializable
