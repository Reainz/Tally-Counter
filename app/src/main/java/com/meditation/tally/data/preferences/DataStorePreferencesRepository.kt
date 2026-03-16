package com.meditation.tally.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.userPreferencesStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class DataStorePreferencesRepository(
    private val context: Context
) : PreferencesRepository {
    private object Keys {
        val targetEnabled = booleanPreferencesKey("target_enabled")
        val targetValue = intPreferencesKey("target_value")
        val hapticsEnabled = booleanPreferencesKey("haptics_enabled")
        val themeMode = stringPreferencesKey("theme_mode")
        val themeColor = stringPreferencesKey("theme_color")
    }

    override fun observePreferences(): Flow<UserPreferences> {
        return context.userPreferencesStore.data
            .catch { exception ->
                if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
                else throw exception
            }
            .map { prefs ->
                UserPreferences(
                    targetEnabled = prefs[Keys.targetEnabled] ?: true,
                    targetValue = (prefs[Keys.targetValue] ?: 108).coerceAtLeast(1),
                    hapticsEnabled = prefs[Keys.hapticsEnabled] ?: true,
                    themeMode = ThemeMode.fromRaw(prefs[Keys.themeMode]),
                    themeColor = ThemeColor.fromRaw(prefs[Keys.themeColor])
                )
            }
    }

    override fun defaultPreferences(): UserPreferences = UserPreferences()

    override suspend fun setTargetEnabled(enabled: Boolean) {
        context.userPreferencesStore.edit { it[Keys.targetEnabled] = enabled }
    }

    override suspend fun setTargetValue(value: Int) {
        context.userPreferencesStore.edit { it[Keys.targetValue] = value.coerceAtLeast(1) }
    }

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        context.userPreferencesStore.edit { it[Keys.hapticsEnabled] = enabled }
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        context.userPreferencesStore.edit { it[Keys.themeMode] = themeMode.name }
    }

    override suspend fun setThemeColor(themeColor: ThemeColor) {
        context.userPreferencesStore.edit { it[Keys.themeColor] = themeColor.name }
    }
}

