package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class Publisher (
    val id: Long,
    val name: Long,
    val description: String,
    val gameCount: Long,
    val listGame: List<Game>
): Serializable
