package com.icongkhanh.gameworld.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameResponse(

    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("released")
    val released: String? = "",
    @SerializedName("tba")
    val tba: Boolean? = false,
    @SerializedName("background_image")
    val background_image: String? = "",
    @SerializedName("rating")
    val rating: Float? = 0f,
    @SerializedName("rating_top")
    val rating_top: Long? = 0,
    @SerializedName("ratings")
    val ratings: List<Ratings>?,
    @SerializedName("ratings_count")
    val ratings_count: Long? = 0,
    @SerializedName("reviews_text_count")
    val reviews_text_count: Long? = 0,
    @SerializedName("added")
    val added: Long? = 0,
    @SerializedName("added_by_status")
    val added_byStatus: AddedByStatus?,
    @SerializedName("metacritic")
    val metacritic: Long? = 0,
    @SerializedName("playtime")
    val playtime: Long? = 0,
    @SerializedName("suggestions_count")
    val suggestions_count: Long? = 0,
    @SerializedName("reviews_count")
    val reviews_count: Long? = 0,
    @SerializedName("saturated_color")
    val saturated_color: String? = "",
    @SerializedName("dominant_color")
    val dominant_color: String? = "",
    @SerializedName("platforms")
    val platforms: List<Platforms>? = emptyList(),
    @SerializedName("short_screenshots")
    val shortScreenshots: List<ShortScreenshots>? = emptyList(),
    @SerializedName("clip")
    val clip: Clip?,
    @SerializedName("genres")
    val genres: List<GenreResponse>? = emptyList(),
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("website")
    val website: String? = ""
)