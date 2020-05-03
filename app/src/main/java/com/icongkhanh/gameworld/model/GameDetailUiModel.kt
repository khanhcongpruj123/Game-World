package com.icongkhanh.gameworld.model

data class GameDetailUiModel(
    val name: String = "",
    val rating: Float = 0F,
    val reviewCount: Long = 0L,
    val imgUrl: String = "",
    val clipUrl: String = "",
    val clipPreviewUrl: String = "",
    val description: String = "",
    val listScreenShot: List<ScreenshotModelUi> = emptyList(),
    val listGenre: List<ItemGenreUiModel> = emptyList(),
    val listMoregame: List<ItemMoreGameUiModel> = emptyList(),
    val requirement: String = "",
    val recommened: String = "",
    val onClickBookMark: () -> Unit,
    val onClickBuy: () -> Unit,
    val onClickClip: () -> Unit,
    val suggestionCount: Long = 0,
    val isBookmark: Boolean = false
)

data class ItemScreenShotUiModel(
    val url: String,
    val onClick: () -> Unit = {}
)

data class ItemGenreUiModel(
    val name: String,
    val onClick: () -> Unit
)

data class ItemMoreGameUiModel(
    val name: String,
    val imgUrl: String,
    val onClick: () -> Unit
)