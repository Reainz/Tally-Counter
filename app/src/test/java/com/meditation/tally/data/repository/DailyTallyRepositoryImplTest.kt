package com.meditation.tally.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.meditation.tally.data.local.database.AppDatabase
import com.meditation.tally.util.DateProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DailyTallyRepositoryImplTest {
    private lateinit var database: AppDatabase
    private lateinit var repository: DailyTallyRepositoryImpl
    private lateinit var dateProvider: FakeDateProvider

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dateProvider = FakeDateProvider(date = LocalDate.parse("2026-03-09"), now = 1000L)
        repository = DailyTallyRepositoryImpl(
            database = database,
            dao = database.dailyRecordDao(),
            dateProvider = dateProvider
        )
    }

    @After
    fun tearDown() {
        if (this::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun `ensureCurrentDateRecord stores target snapshot on create`() = runTest {
        repository.ensureCurrentDateRecord(targetSnapshot = 108)
        val record = database.dailyRecordDao().getRecordByDate("2026-03-09")
        assertThat(record?.target).isEqualTo(108)
    }

    @Test
    fun `history records are not rewritten when target changes`() = runTest {
        repository.ensureCurrentDateRecord(targetSnapshot = 108)
        dateProvider.date = LocalDate.parse("2026-03-10")
        repository.ensureCurrentDateRecord(targetSnapshot = null)

        val all = repository.observeAllRecords().first()
        val old = all.first { it.date == "2026-03-09" }
        val new = all.first { it.date == "2026-03-10" }
        assertThat(old.target).isEqualTo(108)
        assertThat(new.target).isNull()
    }

    @Test
    fun `clearAllAndCreateToday recreates current day at zero`() = runTest {
        repository.ensureCurrentDateRecord(targetSnapshot = 108)
        repository.increment("2026-03-09")

        repository.clearAllAndCreateToday(targetSnapshot = null)
        val all = repository.observeAllRecords().first()
        assertThat(all).hasSize(1)
        assertThat(all.first().date).isEqualTo("2026-03-09")
        assertThat(all.first().count).isEqualTo(0)
    }

    private class FakeDateProvider(
        var date: LocalDate,
        var now: Long
    ) : DateProvider {
        override fun currentLocalDate(): LocalDate = date

        override fun currentDateIso(): String = date.toString()

        override fun currentTimeMillis(): Long = now
    }
}