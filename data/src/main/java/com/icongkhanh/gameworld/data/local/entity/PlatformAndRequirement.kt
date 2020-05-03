package com.icongkhanh.gameworld.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "platform_and_requirement")
data class PlatformAndRequirement(
    @ColumnInfo(name = "_platform")
    val platform: Platform?,
    @ColumnInfo(name = "_releasedDate")
    val releasedDate: String = "",
    @ColumnInfo(name = "_requirementMin")
    val requirementMin: String = "",
    @ColumnInfo(name = "_requirementRecommended")
    val requirementRecommended: String = ""
)