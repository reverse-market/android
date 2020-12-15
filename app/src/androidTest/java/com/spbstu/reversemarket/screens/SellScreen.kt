package com.spbstu.reversemarket.screens

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.utils.TestUtils.Companion.withIndex
import org.hamcrest.Matchers.allOf

class SellScreen : BaseScreen(R.id.layout_toolbar_search__category_name) {

    private val itemName = withId(R.id.layout_product_item__name)
    private val list = withId(R.id.frg_product_list)

    fun makeProposalToItemByIndex(index: Int): ProductScreen? {
        val recyclerMatcher = withIndex(ViewMatchers.hasDescendant(itemName), index)
        return try {
            onView(allOf(list, ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(recyclerMatcher))
            onView(
                allOf(
                    withIndex(itemName, index),
                    ViewMatchers.isDisplayed()
                )
            ).perform(ViewActions.click())
            ProductScreen()
        } catch (e: PerformException) {
            null
        }
    }

}