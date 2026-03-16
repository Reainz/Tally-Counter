package com.meditation.tally.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.meditation.tally.data.local.database.AppDatabase
import com.meditation.tally.data.local.entity.DailyRecordEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DailyRecordDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: DailyRecordDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dailyRecordDao()
    }

    @After
    fun tearDown() {
        if (::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun `observeAllRecords returns descending by date`() = runTest {
        dao.upsert(DailyRecordEntity(date = "2026-03-08", count = 2, target = 108, updatedAt = 1L))
        dao.upsert(DailyRecordEntity(date = "2026-03-09", count = 5, target = 108, updatedAt = 2L))

        val records = dao.observeAllRecords().first()
        assertThat(records.map { it.date }).containsExactly("2026-03-09", "2026-03-08").inOrder()
    }
}
