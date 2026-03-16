package com.meditation.tally.ui.settings

import com.meditation.tally.data.preferences.ThemeColor
import com.meditation.tally.data.preferences.ThemeMode

data class SettingsUiState(
    val targetEnabled: Boolean = true,
    val targetInput: String = "108",
    val savedTargetValue: Int = 108,
    val hapticsEnabled: Boolean = true,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val themeColor: ThemeColor = ThemeColor.ORANGE,
    val showClearHistoryDialog: Boolean = false
)
