package com.meditation.tally.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager

class HistoryViewModelFactory(
    private val repository: DailyTallyRepository,
    private val preferencesRepository: PreferencesRepository,
    private val undoSessionManager: UndoSessionManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(
                repository = repository,
                preferencesRepository = preferencesRepository,
                undoSessionManager = undoSessionManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
