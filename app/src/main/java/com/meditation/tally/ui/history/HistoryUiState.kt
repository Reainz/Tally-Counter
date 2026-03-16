package com.meditation.tally.ui.history

data class HistoryItemUiModel(
    val dateIso: String,
    val dateLabel: String,
    val count: Int,
    val targetSnapshot: Int?
)

data class HistoryUiState(
    val items: List<HistoryItemUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val showClearHistoryDialog: Boolean = false
)
