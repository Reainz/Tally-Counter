package com.meditation.tally.data.repository

import androidx.room.withTransaction
import com.meditation.tally.data.local.dao.DailyRecordDao
import com.meditation.tally.data.local.database.AppDatabase
import com.meditation.tally.data.local.entity.DailyRecordEntity
import com.meditation.tally.util.DateProvider
import kotlinx.coroutines.flow.Flow

class DailyTallyRepositoryImpl(
    private val database: AppDatabase,
    private val dao: DailyRecordDao,
    private val dateProvider: DateProvider
) : DailyTallyRepository {
    override fun observeRecordByDate(date: String): Flow<DailyRecordEntity?> {
        return dao.observeRecordByDate(date)
    }

    override fun observeAllRecords(): Flow<List<DailyRecordEntity>> {
        return dao.observeAllRecords()
    }

    override suspend fun ensureCurrentDateRecord(targetSnapshot: Int?): String {
        val date = dateProvider.currentDateIso()
        ensureRecordExists(date = date, targetSnapshot = targetSnapshot)
        return date
    }

    override suspend fun increment(date: String): DailyRecordEntity {
        val existing = dao.getRecordByDate(date) ?: ensureRecordExists(date = date, targetSnapshot = null)
        val updated = existing.copy(
            count = existing.count + 1,
            updatedAt = dateProvider.currentTimeMillis()
        )
        dao.updateCount(date = date, count = updated.count, updatedAt = updated.updatedAt)
        return updated
    }

    override suspend fun decrement(date: String): DailyRecordEntity {
        val existing = dao.getRecordByDate(date) ?: ensureRecordExists(date = date, targetSnapshot = null)
        val updated = existing.copy(
            count = (existing.count - 1).coerceAtLeast(0),
            updatedAt = dateProvider.currentTimeMillis()
        )
        dao.updateCount(date = date, count = updated.count, updatedAt = updated.updatedAt)
        return updated
    }

    override suspend fun reset(date: String): DailyRecordEntity {
        val existing = dao.getRecordByDate(date) ?: ensureRecordExists(date = date, targetSnapshot = null)
        val updated = existing.copy(
            count = 0,
            updatedAt = dateProvider.currentTimeMillis()
        )
        dao.updateCount(date = date, count = updated.count, updatedAt = updated.updatedAt)
        return updated
    }

    override suspend fun clearAllAndCreateToday(targetSnapshot: Int?): String {
        return database.withTransaction {
            dao.deleteAll()
            ensureCurrentDateRecord(targetSnapshot)
        }
    }

    private suspend fun ensureRecordExists(date: String, targetSnapshot: Int?): DailyRecordEntity {
        val existing = dao.getRecordByDate(date)
        if (existing != null) return existing

        val created = DailyRecordEntity(
            date = date,
            count = 0,
            target = targetSnapshot,
            updatedAt = dateProvider.currentTimeMillis()
        )
        dao.upsert(created)
        return created
    }
}

