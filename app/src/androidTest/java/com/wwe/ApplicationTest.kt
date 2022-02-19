package com.wwe

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.wwe.main.MainActivity
import org.junit.Test

class ApplicationTest {

    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)

        // Should be in Registration/EnterDetails because the user is not registered
        Espresso.onView(ViewMatchers.withText("Register to Dagger World!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.username))
            .perform(ViewActions.typeText("username"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.next)).perform(ViewActions.click())

        // Registration/T&Cs
        Espresso.onView(ViewMatchers.withText("Terms and Conditions"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("REGISTER")).perform(ViewActions.click())

        // Main
        Espresso.onView(ViewMatchers.withText("Hello username!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("SETTINGS")).perform(ViewActions.click())

        // Settings
        Espresso.onView(ViewMatchers.withText("REFRESH NOTIFICATIONS"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("LOGOUT")).perform(ViewActions.click())

        // Login
        Espresso.onView(ViewMatchers.withText("Welcome to Dagger World!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withText("LOGIN")).perform(ViewActions.click())

        // Main
        Espresso.onView(ViewMatchers.withText("Hello username!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("SETTINGS")).perform(ViewActions.click())

        // Settings
        Espresso.onView(ViewMatchers.withText("REFRESH NOTIFICATIONS"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("LOGOUT")).perform(ViewActions.click())

        // Login
        Espresso.onView(ViewMatchers.withText("Welcome to Dagger World!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.unregister)).perform(ViewActions.click())

        // Registration
        Espresso.onView(ViewMatchers.withText("Register to Dagger World!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}