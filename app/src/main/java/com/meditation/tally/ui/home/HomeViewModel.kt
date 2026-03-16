package com.meditation.tally.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meditation.tally.data.preferences.PreferencesRepository
import com.meditation.tally.data.repository.DailyTallyRepository
import com.meditation.tally.data.repository.UndoSessionManager
import com.meditation.tally.domain.TallyRules
import com.meditation.tally.util.DateProvider
import com.meditation.tally.util.DateTextFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: DailyTallyRepository,
    private val preferencesRepository: PreferencesRepository,
    private val undoSessionManager: UndoSessionManager,
    private val dateProvider: DateProvider
) : ViewModel() {
    private val todayDate = MutableStateFlow(dateProvider.currentDateIso())
    private val showResetDialog = MutableStateFlow(false)

    private val todayRecordFlow = todayDate.flatMapLatest { date ->
        repository.observeRecordByDate(date)
    }

    val uiState: StateFlow<HomeUiState> = combine(
        todayDate,
        todayRecordFlow,
        preferencesRepository.observePreferences(),
        showResetDialog
    ) { date, record, prefs, resetDialogVisible ->
        val count = record?.count ?: 0
        val targetEnabled = prefs.targetEnabled
        val targetValue = prefs.targetValue
        HomeUiState(
            formattedDate = DateTextFormatter.formatHomeHeader(
                dateIso = date,
                today = dateProvider.currentLocalDate()
            ),
            count = count,
            targetEnabled = targetEnabled,
            targetValue = targetValue,
            goalLabel = TallyRules.goalLabel(count, targetEnabled, targetValue),
            progress = TallyRules.calculateProgress(count, targetEnabled, targetValue),
            canUndo = count > 0,
            showResetDialog = resetDialogVisible,
            hapticsEnabled = prefs.hapticsEnabled,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeUiState()
    )

    init {
        onHomeResumed()
    }

    fun onHomeResumed() {
        viewModelScope.launch {
            val prefs = preferencesRepository.observePreferences().first()
            val targetSnapshot = if (prefs.targetEnabled) prefs.targetValue else null
            val ensuredDate = repository.ensureCurrentDateRecord(targetSnapshot)
            val dateChanged = ensuredDate != todayDate.value
            todayDate.value = ensuredDate
            if (dateChanged) {
                undoSessionManager.clear()
            }
        }
    }

    fun onTapCounter() {
        viewModelScope.launch {
            repository.increment(todayDate.value)
            undoSessionManager.markIncrement()
        }
    }

    fun onUndo() {
        viewModelScope.launch {
            if (!uiState.value.canUndo) return@launch
            repository.decrement(todayDate.value)
        }
    }

    fun onResetClick() {
        showResetDialog.value = true
    }

    fun onResetDismiss() {
        showResetDialog.value = false
    }

    fun onResetConfirm() {
        viewModelScope.launch {
            repository.reset(todayDate.value)
            showResetDialog.value = false
            undoSessionManager.clear()
        }
    }
}
