package com.icongkhanh.gameworld.domain.model

import java.io.Serializable

data class PlatformAndRequirement(
    val platform: Platform?,
    val releasedDate: String = "",
    val requirementMin: String = "",
    val requirementRecommended: String = ""
): Serializable