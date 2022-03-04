package com.wwe.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.wwe.data.Result
import com.wwe.data.Result.Error
import com.wwe.data.Result.Success
import com.wwe.data.Result.Loading
import com.wwe.data.Task
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeTestRepository : TasksRepository {
    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()
    private var shouldReturnError = false
    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        runBlocking { refreshTasks() }
        // MutableLiveData<Result<List<Task>>>()
        return observableTasks.map { tasks ->
            when (tasks) {
                is Loading -> Loading
                is Error -> Error(tasks.exception)
                is Success -> {
                    val task = tasks.data.firstOrNull { it.id == taskId } ?: return@map Error(Exception("Not found"))
                    Success(task)
                }
            }
        }
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(tasksServiceData.values.toList())
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        tasksServiceData[taskId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find task"))
    }

    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override suspend fun refreshTask(taskId: String) {
        refreshTasks()
    }

    override suspend fun saveTask(task: Task) {
        tasksServiceData[task.id] = task
        refreshTasks()
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = task.copy(isCompleted = true)
        tasksServiceData[task.id] = completedTask
        refreshTasks()
    }

    override suspend fun completeTask(taskId: String) {
        // Not required for the remote data source.
        throw NotImplementedError()
    }

    override suspend fun activateTask(task: Task) {
        val activateTask = task.copy(isCompleted = false)
        tasksServiceData[task.id] = activateTask
        refreshTasks()
    }

    override suspend fun activateTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun clearCompletedTasks() {
        tasksServiceData = tasksServiceData.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
        refreshTasks()
    }

    override suspend fun deleteAllTasks() {
        tasksServiceData.clear()
        refreshTasks()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
        refreshTasks()
    }

    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksServiceData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }
}