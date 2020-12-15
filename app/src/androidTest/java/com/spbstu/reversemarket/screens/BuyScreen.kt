package com.spbstu.reversemarket.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.spbstu.reversemarket.R

class BuyScreen : BaseScreen(R.id.layout_toolbar_search__category_name) {

    private val addNewBtn = ViewMatchers.withId(R.id.layout_toolbar_search__btn)

    fun openAddNew(): BuyInfoScreen {
        onView(addNewBtn).check(matches(isDisplayed())).perform(click())
        return BuyInfoScreen()
    }

}