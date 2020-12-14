package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.orders.adapter.BoughtOrderAdapter
import com.spbstu.reversemarket.orders.domain.BoughtOrder
import kotlinx.android.synthetic.main.fragment_bought_orders.*

class BoughtOrdersFragment : Fragment(R.layout.fragment_bought_orders) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frg_bought__list.adapter =
            BoughtOrderAdapter(emptyList(), Glide.with(this)) { pos, view ->

            }
    }
}