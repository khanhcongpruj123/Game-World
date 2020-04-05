package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class Developer (
    val id: Long = 0,
    val name: String = "",
    val gameCount: Long = 0,
    val imgUrl: String = "",
    val listGame: List<Game> = emptyList()
): Serializable
