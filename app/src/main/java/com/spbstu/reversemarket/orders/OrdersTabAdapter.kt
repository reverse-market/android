package com.spbstu.reversemarket.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class OrdersTabAdapter(
    fm: FragmentManager,
    private val titles: List<String>
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BoughtOrdersFragment()
            1 -> SoldOrdersFragment()
            else -> throw IllegalStateException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> titles[0]
            1 -> titles[1]
            else -> throw IllegalStateException()
        }
    }


}