package com.wwe.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wwe.contract.SampleConfiguration
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any> // <Fragment> ?

    @Inject
    internal lateinit var configuration: SampleConfiguration

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.apply {
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}