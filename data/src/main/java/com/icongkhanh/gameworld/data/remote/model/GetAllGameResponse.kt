package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetAllGameResponse(
    @SerializedName("count")
    val count: Long? = 0,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: String? = "",
    @SerializedName("results")
    val results: List<GameResponse>
)