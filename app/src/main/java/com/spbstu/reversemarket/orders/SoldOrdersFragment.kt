package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.orders.adapter.SoldOrderAdapter
import com.spbstu.reversemarket.orders.domain.SoldOrder
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_sold_orders.*


class SoldOrdersFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_sold_orders) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frg_sold__list.adapter =
            SoldOrderAdapter(emptyList(), Glide.with(this)) { pos, view ->

            }
    }
}