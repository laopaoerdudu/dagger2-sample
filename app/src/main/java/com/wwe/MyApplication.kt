package com.wwe

import android.app.Application
import com.wwe.data.source.TasksRepository
import timber.log.Timber

// Why defined open, bec for test
open class MyApplication : Application() {
    val taskRepository: TasksRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}

