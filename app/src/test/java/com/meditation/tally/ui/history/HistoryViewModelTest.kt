package com.meditation.tally.ui.history

import com.google.common.truth.Truth.assertThat
import com.meditation.tally.data.local.entity.DailyRecordEntity
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.preferences.ThemeMode
import com.meditation.tally.data.preferences.UserPreferences
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.util.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `history includes today and is not empty when records exist`() = runTest {
        val repository = FakeDailyTallyRepository(
            initial = listOf(
                DailyRecordEntity(date = "2026-03-09", count = 10, target = 108, updatedAt = 0L)
            )
        )
        val vm = HistoryViewModel(
            repository = repository,
            preferencesRepository = FakePreferencesRepository(),
            undoSessionManager = UndoSessionManager()
        )
        advanceUntilIdle()

        assertThat(vm.uiState.value.isEmpty).isFalse()
        assertThat(vm.uiState.value.items.first().dateIso).isEqualTo("2026-03-09")
    }

    @Test
    fun `clear history clears records and undo session`() = runTest {
        val repository = FakeDailyTallyRepository(
            initial = listOf(
                DailyRecordEntity(date = "2026-03-09", count = 10, target = 108, updatedAt = 0L),
                DailyRecordEntity(date = "2026-03-08", count = 5, target = 108, updatedAt = 0L)
            )
        )
        val undo = UndoSessionManager().apply { markIncrement() }
        val vm = HistoryViewModel(
            repository = repository,
            preferencesRepository = FakePreferencesRepository(),
            undoSessionManager = undo
        )

        advanceUntilIdle()
        vm.onConfirmClearHistory()
        advanceUntilIdle()

        assertThat(repository.records.value).hasSize(1)
        assertThat(repository.records.value.values.first().count).isEqualTo(0)
        assertThat(undo.canUndo.value).isFalse()
    }

    private class FakePreferencesRepository : PreferencesRepository {
        private val state = MutableStateFlow(UserPreferences())

        override fun observePreferences(): Flow<UserPreferences> = state.asStateFlow()
        override fun defaultPreferences(): UserPreferences = UserPreferences()
        override suspend fun setTargetEnabled(enabled: Boolean) {}
        override suspend fun setTargetValue(value: Int) {}
        override suspend fun setHapticsEnabled(enabled: Boolean) {}
        override suspend fun setThemeMode(themeMode: ThemeMode) {}
    }

    private class FakeDailyTallyRepository(
        initial: List<DailyRecordEntity>
    ) : DailyTallyRepository {
        val records = MutableStateFlow(initial.associateBy { it.date })

        override fun observeRecordByDate(date: String): Flow<DailyRecordEntity?> {
            return records.map { it[date] }
        }

        override fun observeAllRecords(): Flow<List<DailyRecordEntity>> {
            return records.map { it.values.sortedByDescending { record -> record.date } }
        }

        override suspend fun ensureCurrentDateRecord(targetSnapshot: Int?): String = "2026-03-09"
        override suspend fun increment(date: String): DailyRecordEntity = records.value.getValue(date)
        override suspend fun decrement(date: String): DailyRecordEntity = records.value.getValue(date)
        override suspend fun reset(date: String): DailyRecordEntity = records.value.getValue(date)
        override suspend fun clearAllAndCreateToday(targetSnapshot: Int?): String {
            records.value = mapOf(
                "2026-03-09" to DailyRecordEntity(
                    date = "2026-03-09",
                    count = 0,
                    target = targetSnapshot,
                    updatedAt = 0L
                )
            )
            return "2026-03-09"
        }
    }
}
