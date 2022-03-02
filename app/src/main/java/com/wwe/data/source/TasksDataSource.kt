package com.wwe.data.source

import androidx.lifecycle.LiveData
import com.wwe.data.Task
import com.wwe.data.Result

interface TasksDataSource {
    fun observeTasks(): LiveData<Result<List<Task>>>

    fun observeTask(taskId: String): LiveData<Result<Task>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun refreshTasks()

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}