package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Ratings(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("percent")
    val percent: Float? = 0f
)