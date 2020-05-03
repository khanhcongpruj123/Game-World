package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_developer")
data class Developer(
    @ColumnInfo(name = "_id")
    @PrimaryKey
    val id: Long = 0,
    @ColumnInfo(name = "_name")
    val name: String = "",
    @ColumnInfo(name = "_gameCount")
    val gameCount: Long = 0,
    @ColumnInfo(name = "_imgUrl")
    val imgUrl: String = "",
    @ColumnInfo(name = "_listGame")
    val listGame: List<Game> = emptyList()
)
