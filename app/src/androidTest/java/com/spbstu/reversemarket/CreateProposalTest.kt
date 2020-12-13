package com.spbstu.reversemarket

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.spbstu.reversemarket.screens.Navigator
import com.spbstu.reversemarket.utils.TestUtils
import com.spbstu.reversemarket.utils.TestUtils.Companion.AMOUNT
import com.spbstu.reversemarket.utils.TestUtils.Companion.DESCRIPTION
import com.spbstu.reversemarket.utils.TestUtils.Companion.PRICE
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateProposalTest {
    companion object {
        const val TAG = "CreateProductTest"
        const val PROPOSAL_INDEX = 0
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Before
    fun loginIfNot() {
        TestUtils.loginIfNot()
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
    }
}