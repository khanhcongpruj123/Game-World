package com.icongkhanh.gameworld.model

data class SearchGameUiModel(
    val id: Long,
    val thumbnailUrl: String,
    val name: String,
    val onClick: () -> Unit = {}
)