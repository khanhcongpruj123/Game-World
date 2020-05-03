package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "_store")
data class Store(
    @ColumnInfo(name = "_id")
    val id: Long = 0,
    @ColumnInfo(name = "_name")
    val name: String = "",
    @ColumnInfo(name = "_website")
    val website: String = "",
    @ColumnInfo(name = "_imgUrl")
    val imgUrl: String = "",
    @ColumnInfo(name = "game_count")
    val gameCount: Long = 0L,
    @ColumnInfo(name = "list_game")
    val listGame: List<Game> = emptyList()
)
