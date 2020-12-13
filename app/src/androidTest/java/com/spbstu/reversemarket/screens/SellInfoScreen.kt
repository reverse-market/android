package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.spbstu.reversemarket.R

class SellInfoScreen : BaseScreen(R.id.frg_sell_info__title) {

    private val descriptionView = withId(R.id.layout_new_product__description_text)
    private val priceView = withId(R.id.layout_new_product__price)
    private val amountView = withId(R.id.layout_new_product__amount)
    private val saveButton = withId(R.id.frg_sell_info__save_btn)

    fun enterDescription(description: String): SellInfoScreen {
        onView(descriptionView).check(matches(isDisplayed()))
            .perform(typeText(description), closeSoftKeyboard())
            .check(matches(withText(description)))
        return this
    }

    fun enterPrice(price: Int): SellInfoScreen {
        onView(priceView).check(matches(isDisplayed()))
            .perform(typeText(price.toString()), closeSoftKeyboard())
            .check(matches(withText(price.toString())))
        return this
    }

    fun enterAmount(amount: Int): SellInfoScreen {
        onView(amountView).check(matches(isDisplayed()))
            .perform(typeText(amount.toString()), closeSoftKeyboard())
            .check(matches(withText(amount.toString())))
        return this
    }

    fun saveClick(): SellScreen {
        checkVisibility()
        onView(saveButton).check(matches(isDisplayed())).perform(scrollTo(), click())
        return SellScreen()
    }

}