package com.meditation.tally.domain

object TallyRules {
    fun calculateProgress(count: Int, targetEnabled: Boolean, targetValue: Int): Float {
        if (!targetEnabled || targetValue <= 0) return 0f
        return (count.toFloat() / targetValue.toFloat()).coerceIn(0f, 1f)
    }

    fun goalLabel(count: Int, targetEnabled: Boolean, targetValue: Int): String? {
        if (!targetEnabled || targetValue <= 0) return null
        return "$count / $targetValue"
    }

    fun parseValidTarget(input: String): Int? {
        val value = input.toIntOrNull() ?: return null
        return value.takeIf { it > 0 }
    }
}

