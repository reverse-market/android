package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.spbstu.reversemarket.R
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment(R.layout.fragment_orders) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frg_orders__pager.adapter = OrdersTabAdapter(
            childFragmentManager, listOf(
                getString(R.string.bought), getString(R.string.sold)
            ), requireContext()
        )
        frg_orders__tab_layout.setupWithViewPager(frg_orders__pager)
    }

}