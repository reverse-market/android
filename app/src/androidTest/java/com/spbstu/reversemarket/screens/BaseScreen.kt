package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.spbstu.reversemarket.utils.IsDisplayedIdling
import com.spbstu.reversemarket.utils.TestUtils.Companion.sleep

abstract class BaseScreen(private val id: Int) {

    init {
        checkVisibility()
    }

    fun checkVisibility() {
        val matcher = ViewMatchers.withId(id)
        val idlingResource: IdlingResource = IsDisplayedIdling(matcher)
        try {
            IdlingRegistry.getInstance().register(idlingResource)
            sleep()
            Espresso.onView(matcher).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }

}
