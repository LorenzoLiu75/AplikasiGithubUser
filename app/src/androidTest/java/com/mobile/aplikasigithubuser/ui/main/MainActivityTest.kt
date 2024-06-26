package com.mobile.aplikasigithubuser.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mobile.aplikasigithubuser.R
import com.mobile.aplikasigithubuser.helper.EspressoIdlingResource
import com.mobile.aplikasigithubuser.ui.adapter.GithubAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testItemClick() {
        onView(withId(R.id.listUsers)).perform(actionOnItemAtPosition<GithubAdapter.MyViewHolder>(0, click()))

        onView(withId(R.id.detailUserLayout)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}