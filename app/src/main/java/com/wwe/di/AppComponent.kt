package com.wwe.di

import android.content.Context
import com.wwe.login.LoginComponent
import com.wwe.registration.RegistrationComponent
import com.wwe.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Classes annotated with @Singleton will have a unique instance in this Component
@Singleton
// Definition of a Dagger component that adds info from the different modules to the graph
@Component(modules = [AppModule::class, StorageModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun userManager(): UserManager
    fun registrationComponent(): RegistrationComponent.Factory
    fun loginComponent(): LoginComponent.Factory
}