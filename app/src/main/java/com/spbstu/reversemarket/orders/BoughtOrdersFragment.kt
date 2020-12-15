package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.orders.adapter.BoughtOrderAdapter
import com.spbstu.reversemarket.orders.domain.BoughtOrder
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_bought_orders.*

class BoughtOrdersFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_bought_orders) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getBought().observe(viewLifecycleOwner, {
            frg_bought__list.adapter =
                BoughtOrderAdapter(it, Glide.with(this)) { pos, view ->

                }
        })
    }
}