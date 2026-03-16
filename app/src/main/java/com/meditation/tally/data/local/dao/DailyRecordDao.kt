package com.meditation.tally.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.meditation.tally.data.local.entity.DailyRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {
    @Query("SELECT * FROM daily_records WHERE date = :date LIMIT 1")
    suspend fun getRecordByDate(date: String): DailyRecordEntity?

    @Query("SELECT * FROM daily_records WHERE date = :date LIMIT 1")
    fun observeRecordByDate(date: String): Flow<DailyRecordEntity?>

    @Query("SELECT * FROM daily_records ORDER BY date DESC")
    fun observeAllRecords(): Flow<List<DailyRecordEntity>>

    @Upsert
    suspend fun upsert(record: DailyRecordEntity)

    @Query("UPDATE daily_records SET count = :count, updatedAt = :updatedAt WHERE date = :date")
    suspend fun updateCount(date: String, count: Int, updatedAt: Long)

    @Query("DELETE FROM daily_records")
    suspend fun deleteAll()
}

