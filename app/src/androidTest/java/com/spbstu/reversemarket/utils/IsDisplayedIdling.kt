package com.spbstu.reversemarket.utils

import android.view.View
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class IsDisplayedIdling(private val viewMatcher: Matcher<View>) :
    IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    override fun isIdleNow(): Boolean {
        if (!viewMatcher.matches(ViewMatchers.isDisplayed())) {
            resourceCallback!!.onTransitionToIdle()
            return true
        }
        return false
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    override fun getName(): String {
        return this.toString() + viewMatcher.toString()
    }

}

