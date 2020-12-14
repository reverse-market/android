package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment.Companion.CATEGORY_ID
import com.spbstu.reversemarket.filter.data.model.Tag
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
                provideProducts(),
                context,
                Glide.with(this)
            )

        layout_toolbar_search__category_name.setOnClickListener {
            val args = Bundle()
            args.putString(
                CategoryFragment.CATEGORY_NAME,
                layout_toolbar_search__category_name.text.toString()
            )
            findNavController().navigate(R.id.categoryFragment, args)
        }

        val tags = initPrevTags(arguments)
        initFilterParams()

        frg_tags_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        frg_tags_list.adapter =
            TagsAdapter(
                tags,
                R.layout.layout_removable_product_tag,
            )
        layout_toolbar_search__button.setOnClickListener(searchButtonListener)
        layout_toolbar_search__text.setOnKeyListener(Utils(::refreshData).enterListener)

        layout_toolbar__search_close_btn.setOnClickListener {
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
            addSortingParamsToBundle(args)
            findNavController().navigate(R.id.filterFragment, args)
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
        ).observe(viewLifecycleOwner) {
            (frg_product_list.adapter as ProductsAdapter).requests = it
        }
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
        args.putInt(PRODUCT_PROPOSAL_ID, request.bestProposal)
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
        arguments?.getInt(FilterFragment.PRICE_TO)?.run {
            priceTo = this
        }
        arguments?.getString(FilterFragment.SORT)?.run {
            sort = this
        }
    }

    private fun provideProducts(): List<Request> = listOf(
        Request(
            1,
            "Nike кроссовки",
            "Air Force 1 Shadow White Yellow",
            "Nike Air Force 1 - это обновленная версия модели 1982 года со свежими цветовыми решениями и новыми деталями. Этот прочный предмет продолжает...",
            emptyList(),
            150,
            1,
            "zvladn7",
            "03.10.20",
            provideProductTags(),
            3,
            false
        ),
        Request(
            1,
            "Nike кроссовки",
            "Air Force 1 Shadow White Yellow",
            "Nike Air Force 1 - это обновленная версия модели 1982 года со свежими цветовыми решениями и новыми деталями. Этот прочный предмет продолжает...",
            emptyList(),
            42000,
            1,
            "zvladn7",
            "02.10.20",
            provideProductTags2(),
            5,
            false
        ),
    )

    fun provideProductTags(): List<Tag> = listOf(
        Tag(0, "Кроссовки"),
        Tag(0, "Желтый")
    )

    fun provideProductTags2(): List<Tag> =
        listOf(
            Tag(0, "Adidas"),
            Tag(0, "Черный"),
            Tag(0, "Кроссовки"),
            Tag(0, "Adidas"),
            Tag(0, "Черный"),
            Tag(0, "Кроссовки")
        )

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

}