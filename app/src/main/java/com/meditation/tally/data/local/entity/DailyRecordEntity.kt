package com.meditation.tally.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_records")
data class DailyRecordEntity(
    @PrimaryKey val date: String,
    val count: Int,
    val target: Int?,
    val updatedAt: Long
)

