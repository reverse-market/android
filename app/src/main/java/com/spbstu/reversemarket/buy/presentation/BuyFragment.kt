package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.product.presentation.ProductFragment
import com.spbstu.reversemarket.sell.data.model.Request
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter
import com.spbstu.reversemarket.utils.Utils
import com.spbstu.reversemarket.utils.Utils.Companion.closeSearchView
import com.spbstu.reversemarket.utils.Utils.Companion.showSearchView

class BuyFragment : InjectionFragment<BuyViewModel>(R.layout.fragment_buy) {

    private lateinit var titleTextView: TextView
    private lateinit var productList: RecyclerView
    private lateinit var searchButton: FrameLayout
    private lateinit var searchTextBackground: RelativeLayout
    private lateinit var searchText: EditText
    private lateinit var searchCloseBtn: ImageView
    private lateinit var addNewButton: ImageView
    private lateinit var recyclerDataCopy: List<Request>
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkAuth()

        titleTextView = view.findViewById(R.id.layout_toolbar_search__category_name)
        titleTextView.setText(R.string.frg_buy_title)
        titleTextView.setCompoundDrawables(null, null, null, null)
        progressBar = view.findViewById(R.id.frg_buy__progress)

        val navView: BottomNavigationView? = requireActivity().findViewById(R.id.nav_view)
        navView?.visibility = View.VISIBLE

        productList = view.findViewById(R.id.frg_buy__list)
        productList.layoutManager = LinearLayoutManager(context)
        productList.adapter =
            ProductsAdapter(
                emptyList(),
                context,
                Glide.with(this)
            ) {
                val bundle = Bundle()
                bundle.putParcelable(ProductFragment.REQUEST_KEY, it)
                bundle.putBoolean(ProductFragment.IS_SELL, false)
                findNavController().navigate(
                    R.id.action_navigation_buy_to_navigation_product,
                    bundle
                )
            }
        searchTextBackground = view.findViewById(R.id.layout_toolbar_search_text__background)
        searchText = view.findViewById(R.id.layout_toolbar_search__text)
        searchButton = view.findViewById(R.id.layout_toolbar_search__button)

        searchButton.setOnClickListener(searchButtonListener)
        searchText.setOnKeyListener(Utils(::filterRecycler).enterListener)

        searchCloseBtn = view.findViewById(R.id.layout_toolbar__search_close_btn)
        searchCloseBtn.setOnClickListener {
            searchText.setText("", TextView.BufferType.EDITABLE)
            filterRecycler()
            closeSearchView(
                titleTextView,
                searchTextBackground,
                searchCloseBtn,
                searchText,
                activity
            )
        }
        addNewButton = view.findViewById(R.id.layout_toolbar_search__btn)
        addNewButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_add
            )
        )

        view.findViewById<ImageView>(R.id.layout_toolbar_search__btn).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_buy_to_buyInfoFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.createRequest(true).observe(viewLifecycleOwner) {
            if (it != null) {
                progressBar.visibility = View.GONE
                recyclerDataCopy = it
                (productList.adapter as ProductsAdapter).requests = it
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
                progressBar.visibility = View.GONE
            }
        }
    }

    private val searchButtonListener = View.OnClickListener {
        if (titleTextView.visibility == View.VISIBLE) {
            showSearchView(
                titleTextView,
                searchTextBackground,
                searchText,
                searchCloseBtn,
                activity
            )
        } else {
            filterRecycler()
        }
    }

    private fun filterRecycler() {
        val text = searchText.text.toString().trim().toLowerCase()
        val filter = recyclerDataCopy.filter {
            (it.name.contains(text, true) || it.itemName.contains(text, true))
        }
        (productList.adapter as ProductsAdapter).requests = filter
    }
}