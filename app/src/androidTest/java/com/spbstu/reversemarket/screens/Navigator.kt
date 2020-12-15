package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.spbstu.reversemarket.utils.TestUtils.Companion.withIndex

object Navigator {
    private const val BUY_PAGE_INDEX = 0
    private const val SELL_PAGE_INDEX = 1

    fun navigateToBuy(): BuyScreen {
        Espresso.onView(
            withIndex(
                ViewMatchers.withResourceName("icon"),
                BUY_PAGE_INDEX
            )
        ).perform(ViewActions.click())
        return BuyScreen()
    }

    fun navigateToSell(): SellScreen {
        Espresso.onView(
            withIndex(
                ViewMatchers.withResourceName("icon"),
                SELL_PAGE_INDEX
            )
        ).perform(ViewActions.click())
        return SellScreen()
    }

}