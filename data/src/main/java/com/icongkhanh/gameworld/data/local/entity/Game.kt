package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_game")
data class Game(
    @ColumnInfo(name = "_id")
    @PrimaryKey
    val id: Long = 0,
    @ColumnInfo(name = "_name")
    val name: String = "",
    @ColumnInfo(name = "_imgUrl")
    val imgUrl: String = "",
    @ColumnInfo(name = "_rating")
    val rating: Float = 0f
)