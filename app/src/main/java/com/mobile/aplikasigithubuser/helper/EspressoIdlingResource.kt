package com.mobile.aplikasigithubuser.helper

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
object EspressoIdlingResource {
    private const val RESOURCE_NAME = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE_NAME)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}