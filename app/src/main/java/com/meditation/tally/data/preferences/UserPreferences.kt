package com.meditation.tally.data.preferences

data class UserPreferences(
    val targetEnabled: Boolean = true,
    val targetValue: Int = 108,
    val hapticsEnabled: Boolean = true,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val themeColor: ThemeColor = ThemeColor.ORANGE
)

