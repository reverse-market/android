package com.spbstu.reversemarket.utils

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

abstract class TestUtils {

    companion object {
        fun sleep() {
            Espresso.onView(ViewMatchers.isRoot()).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isRoot()
                }

                override fun getDescription(): String {
                    return "some same"
                }

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {
                    uiController.loopMainThreadUntilIdle()
                    uiController.loopMainThreadForAtLeast(2000)
                }
            })
        }

        fun withIndex(
            matcher: Matcher<View?>,
            index: Int
        ): Matcher<View?>? {
            return object : TypeSafeMatcher<View?>() {
                var currentIndex = 0
                override fun describeTo(description: Description) {
                    description.appendText("with index: ")
                    description.appendValue(index)
                    matcher.describeTo(description)
                }

                override fun matchesSafely(view: View?): Boolean {
                    return matcher.matches(view) && currentIndex++ == index
                }
            }
        }
    }

}