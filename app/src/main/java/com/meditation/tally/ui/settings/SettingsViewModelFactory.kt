package com.meditation.tally.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager

class SettingsViewModelFactory(
    private val preferencesRepository: PreferencesRepository,
    private val dailyTallyRepository: DailyTallyRepository,
    private val undoSessionManager: UndoSessionManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                preferencesRepository = preferencesRepository,
                dailyTallyRepository = dailyTallyRepository,
                undoSessionManager = undoSessionManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
