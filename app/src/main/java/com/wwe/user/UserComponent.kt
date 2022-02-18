package com.wwe.user

import com.wwe.main.MainActivity
import dagger.Subcomponent

// Scope annotation that the UserComponent uses
// Classes annotated with @LoggedUserScope will have a unique instance in this Component
@LoggedUserScope
@Subcomponent
interface UserComponent {

    // Factory to create instances of UserComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    fun inject(activity: MainActivity)
    // fun inject(activity: SettingsActivity) TODO: update in next phase
}