package com.meditation.tally.data.preferences

enum class ThemeColor {
    ORANGE,  // Buddhist monk robe–style (saffron)
    BLUE,
    GREEN,
    PINK,
    GREY;

    companion object {
        fun fromRaw(raw: String?): ThemeColor {
            return entries.firstOrNull { it.name == raw } ?: ORANGE
        }
    }
}
