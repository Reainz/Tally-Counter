package com.meditation.tally.util

import java.time.Clock
import java.time.LocalDate

class DefaultDateProvider(
    private val clock: Clock = Clock.systemDefaultZone()
) : DateProvider {
    override fun currentLocalDate(): LocalDate = LocalDate.now(clock)

    override fun currentDateIso(): String = currentLocalDate().toString()

    override fun currentTimeMillis(): Long = clock.millis()
}
