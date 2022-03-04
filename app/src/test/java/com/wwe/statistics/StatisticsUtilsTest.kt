package com.wwe.statistics

import com.wwe.data.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Given
        val tasks = listOf(
            Task("title", "desc", isCompleted = false)
        )

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertThat(result.activeTasksPercent, Is.`is`(100f))
        assertThat(result.completedTasksPercent, Is.`is`(0f))
    }
}