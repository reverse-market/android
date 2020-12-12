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
    private var productId: Int = 0

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_NAME = "PRODUCT_NAME"
        const val PRODUCT_ITEM_NAME = "PRODUCT_ITEM_NAME"
        const val PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION"
        const val PRODUCT_PRICE = "PRODUCT_PRICE"
        const val PRODUCT_QUANTITY = "PRODUCT_QUANTITY"
        const val PRODUCT_DATE = "PRODUCT_DATE"
        const val PRODUCT_TAGS_NAME = "PRODUCT_TAGS_NAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeViewOptions()
        //hide bottom nav
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.GONE
        initFields()
        //setup view pager
        productTabAdapter = ProductTabAdapter(this, provideDescriptionBundle())
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
    }

    private fun initFields() {
        productId = requireArguments().getInt(PRODUCT_ID)
        frg_product_name.text = requireArguments().getString(PRODUCT_NAME)
        frg_product_sub_name.text = requireArguments().getString(PRODUCT_ITEM_NAME)
        frg_product_sub_name.text = requireArguments().getString(PRODUCT_ITEM_NAME)
    }

    private fun provideDescriptionBundle(): Bundle {
        var bundle = Bundle()
        bundle.putInt(PRODUCT_ID, requireArguments().getInt(PRODUCT_ID))
        bundle.putString(PRODUCT_DESCRIPTION, requireArguments().getString(PRODUCT_DESCRIPTION))
        bundle.putInt(PRODUCT_PRICE, requireArguments().getInt(PRODUCT_PRICE))
        bundle.putInt(PRODUCT_QUANTITY, requireArguments().getInt(PRODUCT_QUANTITY))
        bundle.putString(PRODUCT_DATE, requireArguments().getString(PRODUCT_DATE))
        bundle.putStringArray(PRODUCT_TAGS_NAME, requireArguments().getStringArray(PRODUCT_TAGS_NAME))
        return bundle
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

    override fun onDestroy() {
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.VISIBLE
        super.onDestroy()
    }

}
