package com.meditation.tally.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meditation.tally.data.local.dao.DailyRecordDao
import com.meditation.tally.data.local.entity.DailyRecordEntity

@Database(
    entities = [DailyRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyRecordDao(): DailyRecordDao
}

