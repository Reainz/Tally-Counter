package com.meditation.tally.data.preferences

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun fromRaw(raw: String?): ThemeMode {
            return entries.firstOrNull { it.name == raw } ?: SYSTEM
        }
    }
}

