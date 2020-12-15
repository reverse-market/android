package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment.Companion.CATEGORY_ID
import com.spbstu.reversemarket.filter.presentation.FilterFragment
import com.spbstu.reversemarket.product.presentation.ProductFragment
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_PROPOSAL_ID
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_DATE
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_DESCRIPTION
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_ID
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_ITEM_NAME
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_NAME
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_PRICE
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_QUANTITY
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_TAGS_NAME
import com.spbstu.reversemarket.sell.data.model.Request
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.Utils
import com.spbstu.reversemarket.utils.Utils.Companion.initPrevTags
import com.spbstu.reversemarket.utils.Utils.Companion.provideTagsBundle
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.layout_toolbar__search.*

class SellFragment : InjectionFragment<SellViewModel>(R.layout.fragment_sell) {

    private var categoryId: Int = 5
    private var priceFrom: Int = 0
    private var priceTo = 100000
    private var sort = "price_desc"
    private var page = 0
    private var size = 20
    private lateinit var tagsAdapter: TagsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE
        val toolbar: Toolbar = view.findViewById(R.id.frg_search_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        categoryId = arguments?.getInt(CATEGORY_ID) ?: 5


        frg_product_list.addOnItemTouchListener(
            RecyclerItemClickListener(frg_product_list,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        navigateToItem(position)
                    }
                })
        )
        frg_product_list.adapter =
            ProductsAdapter(
                emptyList(),
                context,
                Glide.with(this)
            )

        layout_toolbar_search__category_name.setOnClickListener {
            val args = Bundle()
            args.putString(
                CategoryFragment.CATEGORY_NAME,
                layout_toolbar_search__category_name.text.toString()
            )
            addSortingParamsToBundle(args)
            findNavController().navigate(R.id.action_navigation_sell_to_categoryFragment, args)
        }

        val tags = initPrevTags(arguments)
        initFilterParams()

        tagsAdapter = TagsAdapter(
            tags,
            R.layout.layout_removable_product_tag,
            onRemove = {
                if (it.isEmpty()) {
                    frg_tags_list.visibility = View.GONE
                } else {
                    frg_tags_list.visibility = View.VISIBLE
                }
                refreshData()
                val filterTagsId = it.map { it.id }
                val filterTagsName = it.map { it.name }
                requireArguments().putIntArray(FilterFragment.FILTER_TAGS_IDS, filterTagsId.toIntArray())
                requireArguments().putStringArray(FilterFragment.FILTER_TAGS_NAME, filterTagsName.toTypedArray())
            }
        )
        frg_tags_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        frg_tags_list.adapter = tagsAdapter


        if (tagsAdapter.tags.isEmpty()) {
            frg_tags_list.visibility = View.GONE
        } else {
            frg_tags_list.visibility = View.VISIBLE
        }

        layout_toolbar_search__category_name.text = categoriesList().find { it.id == categoryId }?.name

        layout_toolbar_search__button.setOnClickListener(searchButtonListener)
        layout_toolbar_search__text.setOnKeyListener(Utils(::refreshData).enterListener)

        layout_toolbar__search_close_btn.setOnClickListener {
            layout_toolbar_search__text.setText("", TextView.BufferType.EDITABLE)
            refreshData()
            Utils.closeSearchView(
                layout_toolbar_search__category_name,
                layout_toolbar_search_text__background,
                layout_toolbar__search_close_btn,
                layout_toolbar_search__text,
                activity
            )
        }

        layout_toolbar_search__btn.setOnClickListener {
            val filterTags = (frg_tags_list.adapter as TagsAdapter).tags
            val args = provideTagsBundle(filterTags)
            args.putInt(CATEGORY_ID, categoryId)
            args.putInt(FilterFragment.PRICE_TO, priceTo)
            args.putInt(FilterFragment.PRICE_FROM, priceFrom)
            addSortingParamsToBundle(args)
            findNavController().navigate(R.id.action_navigation_sell_to_filterFragment, args)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getRequests(
            page,
            size,
            categoryId,
            (frg_tags_list.adapter as TagsAdapter).tags,
            priceFrom,
            priceTo,
            sort
        ).observe(viewLifecycleOwner, {
            (frg_product_list.adapter as ProductsAdapter).requests = it
        })
    }

    private fun navigateToItem(position: Int) {
        val request = (frg_product_list.adapter as ProductsAdapter).requests[position]
        findNavController().navigate(
            R.id.action_navigation_sell_to_navigation_product,
            formItemArgs(request)
        )
    }

    private fun formItemArgs(request: Request): Bundle {
        val args = Bundle()
        args.putParcelable(ProductFragment.REQUEST_KEY, request)
        args.putInt(PRODUCT_ID, request.id)
        args.putInt(PRODUCT_PROPOSAL_ID, request.bestProposal?:0)
        args.putString(PRODUCT_NAME, request.name)
        args.putString(PRODUCT_ITEM_NAME, request.itemName)
        args.putString(PRODUCT_DESCRIPTION, request.description)
        args.putInt(PRODUCT_PRICE, request.price)
        args.putInt(PRODUCT_QUANTITY, request.quantity)
        args.putString(PRODUCT_DATE, request.date)
        args.putStringArray(PRODUCT_TAGS_NAME, request.tags.map { it.name }.toTypedArray())
        return args
    }

    private fun addSortingParamsToBundle(bundle: Bundle) {
        bundle.putInt(FilterFragment.PRICE_FROM, priceFrom)
        bundle.putInt(FilterFragment.PRICE_TO, priceTo)
        bundle.putString(FilterFragment.SORT, sort)
        bundle.putInt(CATEGORY_ID, categoryId)
    }

    private fun initFilterParams() {
        arguments?.getString(CategoryFragment.CATEGORY_NAME)?.run {
            layout_toolbar_search__category_name.text = this
        }
        arguments?.getInt(CATEGORY_ID)?.run {
            categoryId = this
        }
        arguments?.getInt(FilterFragment.PRICE_FROM)?.run {
            priceFrom = this
        }
        priceTo = arguments?.getInt(FilterFragment.PRICE_TO) ?: 100000
        arguments?.getString(FilterFragment.SORT)?.run {
            sort = this
        }
    }

    private val searchButtonListener = View.OnClickListener {
        if (layout_toolbar_search__category_name.visibility == View.VISIBLE) {
            Utils.showSearchView(
                layout_toolbar_search__category_name,
                layout_toolbar_search_text__background,
                layout_toolbar_search__text,
                layout_toolbar__search_close_btn,
                activity
            )
        } else {
            refreshData()
        }
    }

    private fun refreshData() {
        viewModel.refreshSearch(
            page,
            size,
            categoryId,
            (frg_tags_list.adapter as TagsAdapter).tags,
            priceFrom,
            priceTo,
            sort,
            layout_toolbar_search__text.text.toString()
        ).observe(viewLifecycleOwner) {
            (frg_product_list.adapter as ProductsAdapter).requests = it
            (frg_product_list.adapter as ProductsAdapter).notifyDataSetChanged()
        }
    }

    fun categoriesList(): List<Category> =
        listOf(
            Category(1, "Недвижимость", ""),
            Category(2, "Электроника", ""),
            Category(3, "Хобби и отдых", ""),
            Category(4, "Транспорт", ""),
            Category(5, "Одежда", ""),
            Category(6, "Животные", ""),
            Category(7, "Для дома", ""),
            Category(8, "Прочее", ""),
        )
}