package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.reversemarket.R
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    private lateinit var productTabAdapter: ProductTabAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        changeViewOptions()
        //hide bottom nav
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.GONE
        //setup view pager
        productTabAdapter = ProductTabAdapter(this)
        viewPager = view.findViewById(R.id.layout_product_item__pager)
        viewPager.adapter = productTabAdapter
        //setup tab layout
        val tabLayout = view.findViewById(R.id.layout_product_item__tab) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = productTabAdapter.getName(position)
        }.attach()
        //on close button listener
        layout_toolbar_product__close.setOnClickListener {
            findNavController().navigateUp()
        }
        return view
    }

    override fun onDestroy() {
        val navView: BottomNavigationView? = requireActivity().findViewById(R.id.nav_view)
        navView?.visibility = View.VISIBLE
        super.onDestroy()
    }

    private fun changeViewOptions() {
        if (findNavController().previousBackStackEntry?.destination?.label ==
            requireActivity().getString(R.string.title_buy)
        ) {
            frg_product_sell_button.visibility = View.GONE
        } else {
            frg_product_sell_button.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_product_to_sellInfoFragment)
            }
        }
    }
}
