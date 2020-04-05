package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class Stores (
    @SerializedName("id")
    val id: Long? = 0L,
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("store")
    val store: Store? = null
)
