package com.wwe.registration

import com.wwe.di.ActivityScope
import com.wwe.registration.detail.EnterDetailsFragment
import com.wwe.registration.terms.TermsAndConditionsFragment
import dagger.Subcomponent

// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
@Subcomponent
interface RegistrationComponent {

    // Factory to create instances of RegistrationComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: RegistrationActivity)
    fun inject(fragment: EnterDetailsFragment)
    fun inject(fragment: TermsAndConditionsFragment)
}