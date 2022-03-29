package com.wwe.di

import com.wwe.registration.RegistrationActivity
import dagger.Module

@Module
abstract class Dagger2SampleBindingModule {

    abstract fun bindingRegistrationActivity(): RegistrationActivity


}