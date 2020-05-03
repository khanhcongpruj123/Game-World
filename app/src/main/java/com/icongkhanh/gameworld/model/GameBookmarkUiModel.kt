package com.icongkhanh.gameworld.model

data class GameBookmarkUiModel(
    val id: Long = 0,
    val name: String = "",
    val rating: Float = 0F,
    val imgUrl: String = "",
    val onClick: () -> Unit
)