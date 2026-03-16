package com.meditation.tally.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateTextFormatter {
    private val homeDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.getDefault())
    private val historyDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())

    fun formatHomeHeader(dateIso: String, today: LocalDate): String {
        return runCatching { LocalDate.parse(dateIso) }
            .getOrNull()
            ?.let { date ->
                val prefix = if (date == today) "Today" else "Day"
                "$prefix \u00B7 ${date.format(homeDateFormatter)}"
            } ?: "Today"
    }

    fun formatHistoryDate(dateIso: String): String {
        return runCatching { LocalDate.parse(dateIso).format(historyDateFormatter) }
            .getOrDefault(dateIso)
    }
}

