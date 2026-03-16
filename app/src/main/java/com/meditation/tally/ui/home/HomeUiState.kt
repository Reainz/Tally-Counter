package com.meditation.tally.ui.home

data class HomeUiState(
    val formattedDate: String = "Today",
    val count: Int = 0,
    val targetEnabled: Boolean = true,
    val targetValue: Int = 108,
    val goalLabel: String? = "0 / 108",
    val progress: Float = 0f,
    val canUndo: Boolean = false,
    val showResetDialog: Boolean = false,
    val hapticsEnabled: Boolean = true,
    val isLoading: Boolean = true
)

