package com.wwe.di

import com.wwe.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindingModule {

    @ContributesAndroidInjector
    abstract fun bindingMainActivity(): MainActivity
}