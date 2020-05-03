package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class Game(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val releasedDate: String = "",
    val updateDate: String = "",
    val imgUrl: String = "",
    val website: String = "",
    val rating: Float = 0f,
    val isBookmark: Boolean = false,
    val ratingsCount: Long = 0L,
    val suggestionsCount: Long = 0L,
    val platforms: List<PlatformAndRequirement> = emptyList(),
    val stores: List<Store> = emptyList(),
    val developer: List<Developer> = emptyList(),
    val genre: List<Genre> = emptyList(),
    val publisher: List<Publisher> = emptyList(),
    val clipUrl: String = "",
    val clipPreviewUrl: String = "",
    val screenShort: List<String> = emptyList(),
    val saturatedColor: String = "#FFFFFF"
): Serializable