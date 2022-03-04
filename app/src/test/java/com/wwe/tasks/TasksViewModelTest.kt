package com.wwe.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wwe.Event
import com.wwe.MainCoroutineRule
import com.wwe.R
import com.wwe.data.Task
import com.wwe.data.source.FakeTestRepository
import com.wwe.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TasksViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var tasksRepository: FakeTestRepository

    @Before
    fun setup() {
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)
        tasksViewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun testAddNewTask() {
        // When
        tasksViewModel.addNewTask()

        // Then
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(
            value.getContentIfNotHandled(),
            CoreMatchers.not(CoreMatchers.nullValue())
        )
    }

    @Test
    fun testSetFiltering() {
        // When
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), CoreMatchers.`is`(true))
    }

    @Test
    fun testCompleteTask_taskCompleted() {
        // Given
        val task = Task("Title", "Description")
        tasksRepository.addTasks(task)

        // When
        tasksViewModel.completeTask(task, true)

        // Then
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, CoreMatchers.`is`(true))
        val snackbarText: Event<Int> =  tasksViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}