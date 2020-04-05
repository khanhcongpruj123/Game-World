package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class Store (
    val id: Long = 0,
    val name: String = "",
    val website: String = "",
    val imgUrl: String = "",
    val gameCount: Long = 0L,
    val listGame: List<Game> = emptyList()
): Serializable
