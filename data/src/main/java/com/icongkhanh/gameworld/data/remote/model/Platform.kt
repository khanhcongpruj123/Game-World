package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("id")
    val id: Long? = 0L,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("games_count")
    val games_count: Long? = 0L,
    @SerializedName("image_background")
    val image_background: String? = ""
)