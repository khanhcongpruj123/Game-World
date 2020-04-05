package com.icongkhanh.gameworld.model

data class ScreenshotModelUi(
    val url: String,
    val onClick: (url: String) -> Unit
)