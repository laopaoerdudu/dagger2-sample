package com.wwe.di

import com.wwe.storage.FakeStorage
import com.wwe.storage.SharedPreferencesStorage
import com.wwe.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class TestStorageModule {
    @Binds
    abstract fun provideStorage(storage: FakeStorage): Storage
}