package com.icongkhanh.gameworld.model

sealed class BookMarkGameDetailState {
    object BookMarkMode : BookMarkGameDetailState()
    object UnBookMarkMode : BookMarkGameDetailState()
}