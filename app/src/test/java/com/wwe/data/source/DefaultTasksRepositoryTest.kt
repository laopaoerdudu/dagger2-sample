package com.wwe.data.source

import com.wwe.data.Result
import com.wwe.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
// Ref: https://github.com/Kotlin/kotlinx.coroutines/blob/c51f795b684034c093562fdae2facde2292e33b7/kotlinx-coroutines-test/README.md
@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }

    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var localDataSource: FakeDataSource
    private lateinit var tasksRepository: DefaultTasksRepository

    // For unit testing.
    private val scope = TestScope()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
        remoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        localDataSource = FakeDataSource(localTasks.toMutableList())

        // Get a reference to the class under test
        tasksRepository = DefaultTasksRepository(
            remoteDataSource, localDataSource, Dispatchers.Main
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = scope.runTest {
        // When
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}