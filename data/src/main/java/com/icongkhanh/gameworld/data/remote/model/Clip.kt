package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Clip (
    @SerializedName("clip")
    val clip: String? = "",
    @SerializedName("preview")
    val preview: String? = ""
)
