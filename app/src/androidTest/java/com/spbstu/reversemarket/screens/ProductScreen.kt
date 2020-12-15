package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.spbstu.reversemarket.R

class ProductScreen : BaseScreen(R.id.layout_toolbar_product__title) {

    private val sellBtn = withId(R.id.frg_product_sell_button)

    fun enterSellScreen(): SellInfoScreen {
        onView(sellBtn).check(matches(isDisplayed())).perform(click())
        return SellInfoScreen()
    }

}