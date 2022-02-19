package com.wwe.registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wwe.MyApplication
import com.wwe.R
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {

    // Stores an instance of RegistrationComponent so that its Fragments can access it
    lateinit var registrationComponent: RegistrationComponent

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        // Creates an instance of Registration component by grabbing the factory from the app graph
        registrationComponent = (application as MyApplication).appComponent
            .registrationComponent().create()

        // Injects this activity to the just created Registration component
        registrationComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }
}
