package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "_publisher")
data class Publisher(
    @ColumnInfo(name = "_id")
    val id: Long,
    @ColumnInfo(name = "_name")
    val name: Long,
    @ColumnInfo(name = "_description")
    val description: String,
    @ColumnInfo(name = "game_count")
    val gameCount: Long,
    @ColumnInfo(name = "list_game")
    val listGame: List<Game>
)
