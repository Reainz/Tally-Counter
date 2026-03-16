package com.meditation.tally.ui.home

import com.google.common.truth.Truth.assertThat
import com.meditation.tally.data.local.entity.DailyRecordEntity
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.preferences.ThemeMode
import com.meditation.tally.data.preferences.UserPreferences
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.util.DateProvider
import com.meditation.tally.util.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class HomeViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `undo decrements repeatedly until zero`() = runTest {
        val repo = FakeDailyTallyRepository()
        val prefs = FakePreferencesRepository()
        val vm = HomeViewModel(
            repository = repo,
            preferencesRepository = prefs,
            undoSessionManager = UndoSessionManager(),
            dateProvider = FakeDateProvider("2026-03-09")
        )

        advanceUntilIdle()
        repeat(3) { vm.onTapCounter() }
        advanceUntilIdle()
        assertThat(vm.uiState.value.count).isEqualTo(3)
        assertThat(vm.uiState.value.canUndo).isTrue()

        vm.onUndo()
        advanceUntilIdle()
        assertThat(vm.uiState.value.count).isEqualTo(2)
        assertThat(vm.uiState.value.canUndo).isTrue()

        vm.onUndo()
        advanceUntilIdle()
        assertThat(vm.uiState.value.count).isEqualTo(1)
        assertThat(vm.uiState.value.canUndo).isTrue()

        vm.onUndo()
        advanceUntilIdle()
        assertThat(vm.uiState.value.count).isEqualTo(0)
        assertThat(vm.uiState.value.canUndo).isFalse()
    }

    @Test
    fun `undo clears when date rolls over`() = runTest {
        val repo = FakeDailyTallyRepository()
        val prefs = FakePreferencesRepository()
        val vm = HomeViewModel(
            repository = repo,
            preferencesRepository = prefs,
            undoSessionManager = UndoSessionManager(),
            dateProvider = FakeDateProvider("2026-03-09")
        )

        advanceUntilIdle()
        vm.onTapCounter()
        advanceUntilIdle()
        assertThat(vm.uiState.value.canUndo).isTrue()

        repo.currentDate = "2026-03-10"
        vm.onHomeResumed()
        advanceUntilIdle()

        assertThat(vm.uiState.value.count).isEqualTo(0)
        assertThat(vm.uiState.value.canUndo).isFalse()
    }

    @Test
    fun `progress updates immediately when settings target changes`() = runTest {
        val repo = FakeDailyTallyRepository()
        val prefs = FakePreferencesRepository()
        val vm = HomeViewModel(
            repository = repo,
            preferencesRepository = prefs,
            undoSessionManager = UndoSessionManager(),
            dateProvider = FakeDateProvider("2026-03-09")
        )

        advanceUntilIdle()
        vm.onTapCounter()
        vm.onTapCounter()
        advanceUntilIdle()
        assertThat(vm.uiState.value.progress).isWithin(0.0001f).of(2f / 108f)

        prefs.setTargetValue(4)
        advanceUntilIdle()
        assertThat(vm.uiState.value.progress).isEqualTo(0.5f)
    }

    private class FakeDateProvider(private val isoDate: String) : DateProvider {
        override fun currentLocalDate(): LocalDate = LocalDate.parse(isoDate)
        override fun currentDateIso(): String = isoDate
        override fun currentTimeMillis(): Long = 0L
    }

    private class FakePreferencesRepository : PreferencesRepository {
        private val state = MutableStateFlow(UserPreferences())

        override fun observePreferences(): Flow<UserPreferences> = state.asStateFlow()

        override fun defaultPreferences(): UserPreferences = UserPreferences()

        override suspend fun setTargetEnabled(enabled: Boolean) {
            state.value = state.value.copy(targetEnabled = enabled)
        }

        override suspend fun setTargetValue(value: Int) {
            state.value = state.value.copy(targetValue = value)
        }

        override suspend fun setHapticsEnabled(enabled: Boolean) {
            state.value = state.value.copy(hapticsEnabled = enabled)
        }

        override suspend fun setThemeMode(themeMode: ThemeMode) {
            state.value = state.value.copy(themeMode = themeMode)
        }
    }

    private class FakeDailyTallyRepository : DailyTallyRepository {
        private val records = MutableStateFlow<Map<String, DailyRecordEntity>>(emptyMap())
        var currentDate: String = "2026-03-09"

        override fun observeRecordByDate(date: String): Flow<DailyRecordEntity?> {
            return records.map { it[date] }
        }

        override fun observeAllRecords(): Flow<List<DailyRecordEntity>> {
            return records.map { it.values.sortedByDescending { record -> record.date } }
        }

        override suspend fun ensureCurrentDateRecord(targetSnapshot: Int?): String {
            if (!records.value.containsKey(currentDate)) {
                records.value = records.value + (currentDate to DailyRecordEntity(currentDate, 0, targetSnapshot, 0))
            }
            return currentDate
        }

        override suspend fun increment(date: String): DailyRecordEntity {
            val current = records.value[date] ?: DailyRecordEntity(date, 0, null, 0)
            val updated = current.copy(count = current.count + 1)
            records.value = records.value + (date to updated)
            return updated
        }

        override suspend fun decrement(date: String): DailyRecordEntity {
            val current = records.value[date] ?: DailyRecordEntity(date, 0, null, 0)
            val updated = current.copy(count = (current.count - 1).coerceAtLeast(0))
            records.value = records.value + (date to updated)
            return updated
        }

        override suspend fun reset(date: String): DailyRecordEntity {
            val current = records.value[date] ?: DailyRecordEntity(date, 0, null, 0)
            val updated = current.copy(count = 0)
            records.value = records.value + (date to updated)
            return updated
        }

        override suspend fun clearAllAndCreateToday(targetSnapshot: Int?): String {
            records.value = mapOf(currentDate to DailyRecordEntity(currentDate, 0, targetSnapshot, 0))
            return currentDate
        }
    }
}
