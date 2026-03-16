package com.meditation.tally.data.repository

import com.meditation.tally.data.local.entity.DailyRecordEntity
import kotlinx.coroutines.flow.Flow

interface DailyTallyRepository {
    fun observeRecordByDate(date: String): Flow<DailyRecordEntity?>
    fun observeAllRecords(): Flow<List<DailyRecordEntity>>

    suspend fun ensureCurrentDateRecord(targetSnapshot: Int?): String
    suspend fun increment(date: String): DailyRecordEntity
    suspend fun decrement(date: String): DailyRecordEntity
    suspend fun reset(date: String): DailyRecordEntity
    suspend fun clearAllAndCreateToday(targetSnapshot: Int?): String
}

