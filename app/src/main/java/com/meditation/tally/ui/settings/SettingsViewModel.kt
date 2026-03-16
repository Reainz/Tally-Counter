package com.meditation.tally.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.preferences.ThemeColor
import com.meditation.tally.data.preferences.ThemeMode
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.domain.TallyRules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val dailyTallyRepository: DailyTallyRepository,
    private val undoSessionManager: UndoSessionManager
) : ViewModel() {
    private val targetInput = MutableStateFlow("108")
    private val showClearHistoryDialog = MutableStateFlow(false)

    val uiState: StateFlow<SettingsUiState> = combine(
        preferencesRepository.observePreferences(),
        targetInput,
        showClearHistoryDialog
    ) { prefs, input, showDialog ->
        val textToDisplay = if (input.isBlank()) prefs.targetValue.toString() else input
        SettingsUiState(
            targetEnabled = prefs.targetEnabled,
            targetInput = textToDisplay,
            savedTargetValue = prefs.targetValue,
            hapticsEnabled = prefs.hapticsEnabled,
            themeMode = prefs.themeMode,
            themeColor = prefs.themeColor,
            showClearHistoryDialog = showDialog
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsUiState()
    )

    init {
        viewModelScope.launch {
            val prefs = preferencesRepository.observePreferences().first()
            targetInput.value = prefs.targetValue.toString()
        }
    }

    fun onTargetEnabledChanged(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.setTargetEnabled(enabled) }
    }

    fun onTargetInputChanged(input: String) {
        targetInput.value = input.filter { it.isDigit() }.take(5)
    }

    fun onSaveTargetClick() {
        viewModelScope.launch {
            val saved = preferencesRepository.observePreferences().first().targetValue
            val parsed = TallyRules.parseValidTarget(targetInput.value)
            if (parsed == null) {
                targetInput.value = saved.toString()
                return@launch
            }
            preferencesRepository.setTargetValue(parsed)
            targetInput.value = parsed.toString()
        }
    }

    fun onHapticsChanged(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.setHapticsEnabled(enabled) }
    }

    fun onThemeSelected(themeMode: ThemeMode) {
        viewModelScope.launch { preferencesRepository.setThemeMode(themeMode) }
    }

    fun onThemeColorSelected(themeColor: ThemeColor) {
        viewModelScope.launch { preferencesRepository.setThemeColor(themeColor) }
    }

    fun onClearHistoryClick() {
        showClearHistoryDialog.value = true
    }

    fun onDismissClearHistoryDialog() {
        showClearHistoryDialog.value = false
    }

    fun onConfirmClearHistory() {
        viewModelScope.launch {
            val prefs = preferencesRepository.observePreferences().first()
            val targetSnapshot = if (prefs.targetEnabled) prefs.targetValue else null
            dailyTallyRepository.clearAllAndCreateToday(targetSnapshot)
            undoSessionManager.clear()
            showClearHistoryDialog.value = false
        }
    }
}
