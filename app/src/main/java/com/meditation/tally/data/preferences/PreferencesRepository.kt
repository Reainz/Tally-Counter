package com.meditation.tally.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun observePreferences(): Flow<UserPreferences>
    fun defaultPreferences(): UserPreferences

    suspend fun setTargetEnabled(enabled: Boolean)
    suspend fun setTargetValue(value: Int)
    suspend fun setHapticsEnabled(enabled: Boolean)
    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun setThemeColor(themeColor: ThemeColor)
}

