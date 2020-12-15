package com.spbstu.reversemarket.product.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spbstu.reversemarket.R

class ProductTabAdapter(
    fragment: ProductFragment,
    private val bundle: Bundle,
    private val context: Context
) : FragmentStateAdapter(fragment) {

    private val tabsList: List<Int> = listOf(R.string.description_tab, R.string.best_proposal_tab)

    override fun getItemCount(): Int {
        return tabsList.size
    }

    fun getName(position: Int) : String {
        return context.resources.getString(tabsList[position])
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DescriptionTabFragment(bundle)
            }
            1 -> {
                BestOfferTabFragment(bundle.getIntArray(ProductFragment.PRODUCT_PROPOSAL_ID), bundle.getBoolean(ProductFragment.IS_SELL), bundle)
            }
            else -> throw IllegalStateException("wrong tab position")
        }
    }

}