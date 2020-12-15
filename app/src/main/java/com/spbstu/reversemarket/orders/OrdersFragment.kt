package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_orders) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frg_orders__pager.adapter = OrdersTabAdapter(
            childFragmentManager, listOf(
                getString(R.string.bought), getString(R.string.sold)
            )
        )
        frg_orders__tab_layout.setupWithViewPager(frg_orders__pager)

        frg_orders__back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}