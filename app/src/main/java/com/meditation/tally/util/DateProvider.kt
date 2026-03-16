package com.meditation.tally.util

import java.time.LocalDate

interface DateProvider {
    fun currentLocalDate(): LocalDate
    fun currentDateIso(): String
    fun currentTimeMillis(): Long
}

