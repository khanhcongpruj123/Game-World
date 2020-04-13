package com.icongkhanh.gameworld.model

data class TopGameUiModel(
    val nameGame: String,
    val starPoint: Float = 0F,
    val clipUrl: String = "",
    val clipPreviewUrl: String = "",
    val onClickBookmark: () -> Unit = {},
    val onClickDetail: () -> Unit = {},
    val onClickBuy: () -> Unit = {}
)