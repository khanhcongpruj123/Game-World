package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreResponse (

    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("games_count")
    val games_count: Long? = 0,
    @SerializedName("image_background")
    val image_background: String? = ""
)
