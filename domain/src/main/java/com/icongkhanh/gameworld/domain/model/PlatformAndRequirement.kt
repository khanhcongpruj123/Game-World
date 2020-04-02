package com.icongkhanh.gameworld.domain.model

data class PlatformAndRequirement(
    val platform: Platform?,
    val releasedDate: String = "",
    val requirementMin: String = "",
    val requirementRecommended: String = ""
)