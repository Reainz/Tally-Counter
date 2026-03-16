package com.meditation.tally.ui.settings

import com.google.common.truth.Truth.assertThat
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.preferences.ThemeMode
import com.meditation.tally.data.preferences.UserPreferences
import com.meditation.tally.util.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `invalid target input is not saved and reverts to last valid`() = runTest {
        val prefs = FakePreferencesRepository()
        val vm = SettingsViewModel(preferencesRepository = prefs)

        advanceUntilIdle()
        vm.onTargetInputChanged("0")
        vm.onSaveTargetClick()
        advanceUntilIdle()

        assertThat(vm.uiState.value.savedTargetValue).isEqualTo(108)
        assertThat(vm.uiState.value.targetInput).isEqualTo("108")
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
}
