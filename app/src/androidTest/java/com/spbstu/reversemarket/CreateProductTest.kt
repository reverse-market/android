package com.spbstu.reversemarket

import android.os.SystemClock
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.spbstu.reversemarket.screens.Navigator
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateProductTest {
    companion object {
        private const val TAG = "CreateProductTest"
        private const val DESCRIPTION = "some description"
        private const val PRICE = 100
        private const val AMOUNT = 1
        private const val PROPOSAL_INDEX = 0
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Before
    fun loginIfNot() {
        try {
            onView(withId(R.id.frg_login__google_login_button))
                .check(matches(isDisplayed()))
                .perform(click())
            Log.i(TAG, "Logged in")
        } catch (e: NoMatchingViewException) {
            Log.i(TAG, "User've been already logged")
        }
    }

    @Test
    fun test() {
        val sellScreen = Navigator.navigateToSell()
        val sellInfoScreen = sellScreen.makeProposalToItemByIndex(PROPOSAL_INDEX)
            .enterSellScreen()
        sellInfoScreen.enterDescription(DESCRIPTION)
            .enterPrice(PRICE)
            .enterAmount(AMOUNT)
            .saveClick()
        SystemClock.sleep(10000)
    }
}