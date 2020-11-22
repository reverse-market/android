package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.reversemarket.R

class ProductFragment : Fragment() {

    private lateinit var productTabAdapter: ProductTabAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        if (navView != null) {
            navView.visibility = View.GONE
        }
        productTabAdapter = ProductTabAdapter(this)
        viewPager = view.findViewById(R.id.layout_product_item__pager)
        viewPager.adapter = productTabAdapter
        val tabLayout = view.findViewById(R.id.layout_product_item__tab) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = productTabAdapter.getName(position)
        }.attach()
    }

    override fun onDetach() {
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        if (navView != null) {
            navView.visibility = View.VISIBLE
        }
        super.onDetach()
    }
}
