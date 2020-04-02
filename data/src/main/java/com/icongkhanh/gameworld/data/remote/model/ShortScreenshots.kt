package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class ShortScreenshots (
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("image")
    val url: String? = ""
)
