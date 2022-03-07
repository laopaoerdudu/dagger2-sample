package com.wwe

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.wwe.data.Task
import com.wwe.data.source.TasksRepository
import com.wwe.tasks.TasksActivity
import com.wwe.util.DataBindingIdlingResource
import com.wwe.util.EspressoIdlingResource
import com.wwe.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Large End-to-End test for the tasks module.
 *
 * UI tests usually use [ActivityTestRule] but there's no API to perform an action before
 * each test. The workaround is to use `ActivityScenario.launch()` and `ActivityScenario.close()`.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class TasksActivityTest {

    private lateinit var repository: TasksRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        repository =
            ServiceLocator.provideTasksRepository(
                ApplicationProvider.getApplicationContext()
            )
        runBlocking {
            repository.deleteAllTasks()
        }
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun editTask() = runBlocking {
        repository.saveTask(Task("TITLE1", "DESCRIPTION"))

        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the task on the list and verify that all the data is correct
        Espresso.onView(ViewMatchers.withText("TITLE1")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.task_detail_title_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("TITLE1")))
        Espresso.onView(withId(R.id.task_detail_description_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("DESCRIPTION")))
        Espresso.onView(withId(R.id.task_detail_complete_checkbox))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isChecked())))

        // Click on the edit button, edit, and save
        Espresso.onView(withId(R.id.edit_task_fab)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.add_task_title_edit_text))
            .perform(ViewActions.replaceText("NEW TITLE"))
        Espresso.onView(withId(R.id.add_task_description_edit_text))
            .perform(ViewActions.replaceText("NEW DESCRIPTION"))
        Espresso.onView(withId(R.id.save_task_fab)).perform(ViewActions.click())

        // Verify task is displayed on screen in the task list.
        Espresso.onView(ViewMatchers.withText("NEW TITLE"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // Verify previous task is not displayed
        Espresso.onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.doesNotExist())

        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Test
    fun createOneTask_deleteTask() {

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Add active task
        Espresso.onView(withId(R.id.add_task_fab)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.add_task_title_edit_text))
            .perform(ViewActions.typeText("TITLE1"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.add_task_description_edit_text))
            .perform(ViewActions.typeText("DESCRIPTION"))
        Espresso.onView(withId(R.id.save_task_fab)).perform(ViewActions.click())

        // Open it in details view
        Espresso.onView(ViewMatchers.withText("TITLE1")).perform(ViewActions.click())
        // Click delete task in menu
        Espresso.onView(withId(R.id.menu_delete)).perform(ViewActions.click())

        // Verify it was deleted
        Espresso.onView(withId(R.id.menu_filter)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.nav_all)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.doesNotExist())
        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }
}