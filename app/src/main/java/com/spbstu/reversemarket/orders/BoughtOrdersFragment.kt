package com.spbstu.reversemarket.orders

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        viewModel.getBought().observe(viewLifecycleOwner) {
            if (it != null) {
                frg_bought__progress.visibility = View.GONE
                frg_bought__list.adapter =
                    BoughtOrderAdapter(it, Glide.with(this)) { pos, view ->

                    }
            } else {
                frg_bought__progress.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}