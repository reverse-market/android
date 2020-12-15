package com.spbstu.reversemarket.utils

import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.spbstu.reversemarket.CreateProposalTest
import com.spbstu.reversemarket.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

abstract class TestUtils {

    companion object {
        const val DESCRIPTION = "some description"
        const val NAME = "name"
        const val ITEM_NAME = "item name"
        const val PRICE = 100
        const val AMOUNT = 1

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

        fun loginIfNot() {
            try {
                Espresso.onView(ViewMatchers.withId(R.id.frg_login__google_login_button))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    .perform(ViewActions.click())
                Log.i(CreateProposalTest.TAG, "Logged in")
            } catch (e: NoMatchingViewException) {
                Log.i(CreateProposalTest.TAG, "User've been already logged")
            }
        }

        val toastMatcher: TypeSafeMatcher<Root> =
            object : TypeSafeMatcher<Root>() {
                override fun describeTo(description: Description) {
                    description.appendText("is toast")
                }

                override fun matchesSafely(root: Root): Boolean {
                    val type = root.windowLayoutParams.get().type
                    if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                        val windowToken = root.decorView.windowToken
                        val appToken = root.decorView.applicationWindowToken
                        return windowToken === appToken
                    }
                    return false
                }
            }
    }

}