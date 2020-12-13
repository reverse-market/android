package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.spbstu.reversemarket.R

class BuyInfoScreen : BaseScreen(R.id.frg_buy_info__title) {

    private val descriptionView = ViewMatchers.withId(R.id.layout_new_product__description_text)
    private val priceView = ViewMatchers.withId(R.id.layout_new_product__price)
    private val amountView = ViewMatchers.withId(R.id.layout_new_product__amount)
    private val nameView = ViewMatchers.withId(R.id.layout_new_product__name)
    private val itemNameView = ViewMatchers.withId(R.id.layout_new_product__itemName)
    private val saveButton = ViewMatchers.withId(R.id.frg_buy_info__save_btn)

    fun enterDescription(description: String): BuyInfoScreen {
        onView(descriptionView).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.typeText(description), ViewActions.closeSoftKeyboard())
            .check(ViewAssertions.matches(ViewMatchers.withText(description)))
        return this
    }

    fun enterPrice(price: Int): BuyInfoScreen {
        onView(priceView).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.typeText(price.toString()), ViewActions.closeSoftKeyboard())
            .check(ViewAssertions.matches(ViewMatchers.withText(price.toString())))
        return this
    }

    fun enterAmount(amount: Int): BuyInfoScreen {
        onView(amountView).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.typeText(amount.toString()), ViewActions.closeSoftKeyboard())
            .check(ViewAssertions.matches(ViewMatchers.withText(amount.toString())))
        return this
    }

    fun enterName(name: String): BuyInfoScreen {
        onView(nameView).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard())
            .check(ViewAssertions.matches(ViewMatchers.withText(name)))
        return this
    }

    fun enterItemName(itemName: String): BuyInfoScreen {
        onView(itemNameView).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                ViewActions.scrollTo(),
                ViewActions.typeText(itemName),
                ViewActions.closeSoftKeyboard()
            )
            .check(ViewAssertions.matches(ViewMatchers.withText(itemName)))
        return this
    }

    fun saveClick(): SellScreen {
        checkVisibility()
        for (i in 0..10) {
            onView(itemNameView).perform(ViewActions.swipeUp())
        }
        onView(saveButton).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                ViewActions.scrollTo(),
                ViewActions.click()
            )
        return SellScreen()
    }

}