package com.meditation.tally.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.util.DateProvider

class HomeViewModelFactory(
    private val repository: DailyTallyRepository,
    private val preferencesRepository: PreferencesRepository,
    private val undoSessionManager: UndoSessionManager,
    private val dateProvider: DateProvider
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                repository = repository,
                preferencesRepository = preferencesRepository,
                undoSessionManager = undoSessionManager,
                dateProvider = dateProvider
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

