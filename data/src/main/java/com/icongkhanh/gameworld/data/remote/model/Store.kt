package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("games_count")
    val games_count: Int,
    @SerializedName("image_background")
    val image_background: String
)