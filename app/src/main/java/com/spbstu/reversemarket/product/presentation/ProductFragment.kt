package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.di.NetworkModule
import com.spbstu.reversemarket.sell.data.model.Request
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    private lateinit var productTabAdapter: ProductTabAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var request: Request

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_PROPOSAL_ID = "PRODUCT_BEST_PROPOSAL_ID"
        const val PRODUCT_NAME = "PRODUCT_NAME"
        const val PRODUCT_ITEM_NAME = "PRODUCT_ITEM_NAME"
        const val PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION"
        const val PRODUCT_PRICE = "PRODUCT_PRICE"
        const val PRODUCT_QUANTITY = "PRODUCT_QUANTITY"
        const val PRODUCT_DATE = "PRODUCT_DATE"
        const val PRODUCT_TAGS_NAME = "PRODUCT_TAGS_NAME"
        const val IS_SELL = "IS_SELL"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFields()
        val bundle = provideDescriptionBundle()
        changeViewOptions(bundle)
        //hide bottom nav
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.GONE
        initFields()
        //setup view pager
        productTabAdapter = ProductTabAdapter(this, bundle, requireContext())
        viewPager = view.findViewById(R.id.layout_product_item__pager)
        viewPager.adapter = productTabAdapter
        //setup tab layout
        val tabLayout = view.findViewById(R.id.layout_product_item__tab) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = productTabAdapter.getName(position)
        }.attach()
        //on close button listener
        layout_toolbar_product__close.setOnClickListener {
            findNavController().popBackStack()
        }

        if (request.photos.isNotEmpty()) {
            Glide.with(this)
                .load(NetworkModule.DATA_BASE_URL + request.photos[0])
                .centerCrop()
                .into(frg_product__image)
        }

        if (requireArguments().getBoolean(IS_SELL)) {
            frg_product_sell_button.visibility = View.VISIBLE
        } else {
            frg_product_sell_button.visibility = View.GONE
        }
        frg_product_name.text = request.name
        frg_product_sub_name.text = request.itemName
    }

    private fun initFields() {
        request = requireArguments().getParcelable(REQUEST_KEY)
            ?: throw IllegalArgumentException("Request must be provided")
        Log.d("WWWW", "$request")
        frg_product_name.text = requireArguments().getString(PRODUCT_NAME)
        frg_product_sub_name.text = requireArguments().getString(PRODUCT_ITEM_NAME)
    }

    private fun provideDescriptionBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(PRODUCT_ID, request.id)
        bundle.putParcelable(REQUEST_KEY, request)
        bundle.putIntArray(
            PRODUCT_PROPOSAL_ID,
            intArrayOf(requireArguments().getInt(PRODUCT_PROPOSAL_ID))
        )
        bundle.putString(PRODUCT_DESCRIPTION, requireArguments().getString(PRODUCT_DESCRIPTION))
        bundle.putInt(PRODUCT_PRICE, requireArguments().getInt(PRODUCT_PRICE))
        bundle.putInt(PRODUCT_QUANTITY, requireArguments().getInt(PRODUCT_QUANTITY))
        bundle.putString(PRODUCT_DATE, requireArguments().getString(PRODUCT_DATE))
        bundle.putStringArray(
            PRODUCT_TAGS_NAME,
            requireArguments().getStringArray(PRODUCT_TAGS_NAME)
        )
        return bundle
    }

    private fun changeViewOptions(bundle: Bundle) {
        if (findNavController().previousBackStackEntry?.destination?.label ==
            requireActivity().getString(R.string.title_buy)
        ) {
            frg_product_sell_button.visibility = View.GONE
            bundle.putBoolean(IS_SELL, requireArguments().getBoolean(IS_SELL))
        } else {
            bundle.putBoolean(IS_SELL, requireArguments().getBoolean(IS_SELL))
            frg_product_sell_button.setOnClickListener {
                val args = Bundle()
                args.putInt(PRODUCT_ID, request.id)
                findNavController().navigate(
                    R.id.action_navigation_product_to_sellInfoFragment,
                    args
                )
            }
        }
    }

    override fun onDestroy() {
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.VISIBLE
        super.onDestroy()
    }

}
