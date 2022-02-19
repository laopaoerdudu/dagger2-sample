package com.wwe

import com.wwe.di.AppComponent
import com.wwe.di.DaggerTestAppComponent

class MyTestApplication : MyApplication()  {

    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.create()
    }
}