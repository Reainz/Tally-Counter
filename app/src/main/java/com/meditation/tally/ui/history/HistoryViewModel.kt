package com.meditation.tally.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.util.DateTextFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: DailyTallyRepository,
    private val preferencesRepository: PreferencesRepository,
    private val undoSessionManager: UndoSessionManager
) : ViewModel() {
    private val showClearHistoryDialog = MutableStateFlow(false)

    val uiState: StateFlow<HistoryUiState> = combine(
        repository.observeAllRecords(),
        showClearHistoryDialog
    ) { records, showDialog ->
            val items = records.map { record ->
                HistoryItemUiModel(
                    dateIso = record.date,
                    dateLabel = DateTextFormatter.formatHistoryDate(record.date),
                    count = record.count,
                    targetSnapshot = record.target
                )
            }
            HistoryUiState(
                items = items,
                isLoading = false,
                isEmpty = items.isEmpty(),
                showClearHistoryDialog = showDialog
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = HistoryUiState()
        )

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
            repository.clearAllAndCreateToday(targetSnapshot)
            undoSessionManager.clear()
            showClearHistoryDialog.value = false
        }
    }
}
