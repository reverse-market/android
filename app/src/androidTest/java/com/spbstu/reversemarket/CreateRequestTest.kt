package com.spbstu.reversemarket

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.spbstu.reversemarket.screens.Navigator
import com.spbstu.reversemarket.utils.TestUtils
import com.spbstu.reversemarket.utils.TestUtils.Companion.AMOUNT
import com.spbstu.reversemarket.utils.TestUtils.Companion.DESCRIPTION
import com.spbstu.reversemarket.utils.TestUtils.Companion.ITEM_NAME
import com.spbstu.reversemarket.utils.TestUtils.Companion.NAME
import com.spbstu.reversemarket.utils.TestUtils.Companion.PRICE
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateRequestTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Before
    fun loginIfNot() {
        TestUtils.loginIfNot()
    }

    @Test
    fun createRequestTest() {
        val buyScreen = Navigator.navigateToBuy()
        buyScreen.openAddNew()
            .enterDescription(DESCRIPTION)
            .enterPrice(PRICE)
            .enterAmount(AMOUNT)
            .enterName(NAME)
            .swipeUp()
            .enterItemName(ITEM_NAME)
            .saveClick()
    }


    @Test
    fun typeNotParams() {
        val buyScreen = Navigator.navigateToBuy()
        val buyInfoScreen = buyScreen.openAddNew()
        assertThrows(NoMatchingViewException::class.java) {
            buyInfoScreen
                .enterDescription(DESCRIPTION)
                .enterPrice(PRICE)
                .swipeUp()
                .enterItemName(ITEM_NAME)
                .saveClick()
        }
        buyInfoScreen.checkVisibility()
    }

}


