package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductTabAdapter(
    fragment: ProductFragment,
    private val bundle: Bundle
) : FragmentStateAdapter(fragment) {

    private val tabsList: List<String> = listOf("описание", "лучшее предложение")

    override fun getItemCount(): Int {
        return tabsList.size
    }

    fun getName(position: Int) : String {
        return tabsList[position]
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DescriptionTabFragment(bundle)
            }
            1 -> {
                BestOfferTabFragment()
            }
            else -> throw IllegalStateException("wrong tab position")
        }
    }

}