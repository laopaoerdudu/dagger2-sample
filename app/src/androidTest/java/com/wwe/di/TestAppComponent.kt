package com.wwe.di

import dagger.Component
import javax.inject.Singleton

// Replacement for AppComponent in android tests
@Singleton
// Includes TestStorageModule that overrides objects provided in StorageModule
@Component(modules = [AppModule::class, TestStorageModule::class])
interface TestAppComponent : AppComponent