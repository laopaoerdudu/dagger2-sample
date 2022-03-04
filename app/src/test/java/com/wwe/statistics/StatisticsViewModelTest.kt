package com.wwe.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wwe.MainCoroutineRule
import com.wwe.data.source.FakeTestRepository
import com.wwe.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [StatisticsViewModel]
 */
@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var statisticsViewModel: StatisticsViewModel

    private lateinit var tasksRepository: FakeTestRepository

    @Before
    fun setUp() {
        // We initialise the repository with no tasks
        tasksRepository = FakeTestRepository()
        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun testRefresh_getTaskThrowError() {
        // Given
        tasksRepository.setReturnError(true)

        // When
        statisticsViewModel.refresh()

        // Then an error message is shown
        assertThat(statisticsViewModel.empty.getOrAwaitValue(), CoreMatchers.`is`(true))
    }

    @Test
    fun testRefresh_dataLoading_statuChange() {
        // Given
        mainCoroutineRule.pauseDispatcher()

        // When
        statisticsViewModel.refresh()

        // Then
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(true))

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(false))
    }
}