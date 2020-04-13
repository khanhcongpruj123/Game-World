package com.icongkhanh.gameworld.model

data class ListTopGameUiModel(
    val list: List<ItemTopGameUiModel>
)

data class ItemTopGameUiModel(
    val id: Long = 0L,
    val name: String = "",
    val thumbnailUrl: String = "",
    val clipUrl: String = "",
    val clipPreviewUrl: String = "",
    val listGenre: List<String> = emptyList(),
    val rating: Float = 0F,
    val ratingCount: Long = 0L,
    val onClick: () -> Unit = {}
)