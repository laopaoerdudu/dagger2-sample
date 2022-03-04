package com.wwe.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wwe.MainCoroutineRule
import com.wwe.data.source.FakeTestRepository
import com.wwe.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.resumeDispatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
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

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var tasksRepository: FakeTestRepository


    @Before
    fun setUp() {
        // We initialise the repository with no tasks
        tasksRepository = FakeTestRepository()

        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
    fun loadTasks_loading() {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the viewmodel
        statisticsViewModel.refresh()

        // Then progress indicator is shown
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(true))

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(false))
    }
}