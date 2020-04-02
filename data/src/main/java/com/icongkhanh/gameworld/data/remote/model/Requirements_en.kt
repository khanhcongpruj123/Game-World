package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Requirements_en(
    @SerializedName("minimum")
    val minimum: String? = "",
    @SerializedName("recommended")
    val recommended: String? = ""
)