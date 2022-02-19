package com.wwe.di

import com.wwe.login.LoginComponent
import com.wwe.registration.RegistrationComponent
import com.wwe.user.UserComponent
import dagger.Module

@Module(subcomponents = [UserComponent::class, RegistrationComponent::class, LoginComponent::class])
class AppModule