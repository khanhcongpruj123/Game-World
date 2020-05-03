package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_platform")
data class Platform(
    @ColumnInfo(name = "_id")
    @PrimaryKey
    val id: Long = 0,
    @ColumnInfo(name = "_name")
    val name: String = "",
    @ColumnInfo(name = "_imgUrl")
    val imgUrl: String = "",
    @ColumnInfo(name = "_description")
    val description: String = "",
    @ColumnInfo(name = "_gameCount")
    val gameCount: Long = 0,
    @ColumnInfo(name = "_listGame")
    val listGame: List<Game> = emptyList()
)
