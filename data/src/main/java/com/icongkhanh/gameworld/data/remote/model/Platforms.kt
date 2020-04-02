package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Platforms(
    @SerializedName("platform")
    val platform: Platform?,
    @SerializedName("released_at")
    val released_at: String? = "",
    @SerializedName("requirements_en")
    val requirements_en: Requirements_en?
)