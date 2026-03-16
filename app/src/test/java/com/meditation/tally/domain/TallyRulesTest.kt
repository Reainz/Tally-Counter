package com.meditation.tally.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TallyRulesTest {
    @Test
    fun `calculateProgress returns zero when disabled`() {
        assertThat(TallyRules.calculateProgress(count = 50, targetEnabled = false, targetValue = 108))
            .isEqualTo(0f)
    }

    @Test
    fun `calculateProgress caps at one`() {
        assertThat(TallyRules.calculateProgress(count = 200, targetEnabled = true, targetValue = 108))
            .isEqualTo(1f)
    }

    @Test
    fun `goalLabel hidden when target disabled`() {
        assertThat(TallyRules.goalLabel(count = 5, targetEnabled = false, targetValue = 108))
            .isNull()
    }

    @Test
    fun `parseValidTarget rejects invalid input`() {
        assertThat(TallyRules.parseValidTarget("0")).isNull()
        assertThat(TallyRules.parseValidTarget("")).isNull()
        assertThat(TallyRules.parseValidTarget("-3")).isNull()
    }
}

