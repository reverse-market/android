package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.sell.domain.model.Product
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.Utils


class SellFragment : Fragment() {

    private lateinit var sellViewModel: SellViewModel
    private lateinit var productList: RecyclerView
    private lateinit var tagsList: RecyclerView
    private lateinit var searchButton: FrameLayout
    private lateinit var categoryNameToolbar: TextView
    private lateinit var searchTextBackground: RelativeLayout
    private lateinit var searchText: EditText
    private lateinit var searchCloseBtn: ImageView
    private lateinit var filterBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_sell, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE
        val toolbar: Toolbar = view.findViewById(R.id.frg_search_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        productList = view.findViewById(R.id.frg_product_list)

        productList.addOnItemTouchListener(RecyclerItemClickListener(productList,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    findNavController().navigate(R.id.action_navigation_sell_to_navigation_product)
                }
            }))

        tagsList = view.findViewById(R.id.frg_tags_list)
        productList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            )

        categoryNameToolbar = view.findViewById(R.id.layout_toolbar_search__category_name)
        categoryNameToolbar.setOnClickListener {
            val args = Bundle()
            args.putString(CategoryFragment.CATEGORY_NAV_PARAMETER, categoryNameToolbar.text.toString())
            findNavController().navigate(R.id.categoryFragment, args)
        }

        val tags = arguments?.getStringArray("FILTER_TAGS")?.toList()
        arguments?.getString(CategoryFragment.CATEGORY_NAV_PARAMETER)?.run {
            categoryNameToolbar.text = this
        }

        tagsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tagsList.adapter =
            TagsAdapter(
                tags ?: provideTags(),
                R.layout.layout_removable_product_tag,
                ::filterRecycler
            )
        searchTextBackground = view.findViewById(R.id.layout_toolbar_search_text__background)
        searchText = view.findViewById(R.id.layout_toolbar_search__text)
        searchButton = view.findViewById(R.id.layout_toolbar_search__button)

        searchButton.setOnClickListener(searchButtonListener)
        searchText.setOnKeyListener(Utils(::filterRecycler).enterListener)

        searchCloseBtn = view.findViewById(R.id.layout_toolbar__search_close_btn)
        searchCloseBtn.setOnClickListener {
            Utils.closeSearchView(categoryNameToolbar, searchTextBackground, searchCloseBtn, searchText, activity)
        }

        filterBtn = view.findViewById(R.id.layout_toolbar_search__btn)
        filterBtn.setOnClickListener{
            val args = Bundle()
            val filterTags = (tagsList.adapter as TagsAdapter).tags
            args.putStringArray("FILTER_TAGS", filterTags.toTypedArray())
            findNavController().navigate(R.id.filterFragment, args)
        }

        return view
    }

    private fun provideProducts(): List<Product> = listOf(
        Product(
            "Nike кроссовки",
            "Air Force 1 Shadow White Yellow",
            150,
            provideProductTags(),
            "Nike Air Force 1 - это обновленная версия модели 1982 года со свежими цветовыми решениями и новыми деталями. Этот прочный предмет продолжает...",
            "$132.10",
            "zvladn7",
            "03.10.20"
        ),
        Product(
            "Adidas кроссовки",
            "Yeezy boost 500",
            524,
            provideProductTags2(),
            "Кроссовки Yeezy 500 от Yeezy. Закругленный носок, шнуровка спереди, сетчатые вставки и резиновая подошва в рубчик. Черный цвет.",
            "Р42000",
            "zvladn7",
            "02.10.20"
        ),
    )

    fun provideProductTags(): List<String> = listOf("Кроссовки", "Желтый")
    fun provideProductTags2(): List<String> =
        listOf("Adidas", "Черный", "Кроссовки", "Adidas", "Черный", "Кроссовки")

    fun provideTags(): List<String> = listOf("Обувь", "Кроссовки", "Санкт-Петербург")

    private fun filterRecycler() {
        val text = searchText.text.toString().trim().toLowerCase()
        val filter = provideProducts().filter {
            (it.name.contains(text, true) || it.fullName.contains(text, true))
                    && (tagsList.adapter as TagsAdapter).tags.intersect(it.tags).isNotEmpty()
        }
        (productList.adapter as ProductsAdapter).products = filter
    }

    private val searchButtonListener = View.OnClickListener {
        if (categoryNameToolbar.visibility == View.VISIBLE) {
            Utils.showSearchView(
                categoryNameToolbar,
                searchTextBackground,
                searchText,
                searchCloseBtn,
                activity
            )
        } else {
            filterRecycler()
        }
    }

}