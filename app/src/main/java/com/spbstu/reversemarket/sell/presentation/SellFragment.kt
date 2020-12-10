package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment.Companion.CATEGORY_ID
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.domain.model.Product
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.Utils
import com.spbstu.reversemarket.utils.Utils.Companion.initPrevTags
import com.spbstu.reversemarket.utils.Utils.Companion.provideTagsBundle
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.layout_toolbar__search.*

class SellFragment : InjectionFragment<SellViewModel>(R.layout.fragment_sell) {

    private var categoryId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE
        val toolbar: Toolbar = view.findViewById(R.id.frg_search_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        frg_product_list.addOnItemTouchListener(
            RecyclerItemClickListener(frg_product_list,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        findNavController().navigate(R.id.action_navigation_sell_to_navigation_product)
                    }
                })
        )
        frg_product_list.adapter =
            ProductsAdapter(
                provideProducts(),
                context
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
        arguments?.getString(CategoryFragment.CATEGORY_NAME)?.run {
            layout_toolbar_search__category_name.text = this
        }
        arguments?.getInt(CategoryFragment.CATEGORY_ID)?.run {
            categoryId = this
        }

        frg_tags_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        frg_tags_list.adapter =
            TagsAdapter(
                tags,
                R.layout.layout_removable_product_tag,
                ::filterRecycler
            )
        layout_toolbar_search__button.setOnClickListener(searchButtonListener)
        layout_toolbar_search__text.setOnKeyListener(Utils(::filterRecycler).enterListener)

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
            findNavController().navigate(R.id.filterFragment, args)
        }
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

    private fun filterRecycler() {
        val text = layout_toolbar_search__text.text.toString().trim().toLowerCase()
        val filter = provideProducts().filter {
            (it.name.contains(text, true) || it.fullName.contains(text, true))
                    && (frg_tags_list.adapter as TagsAdapter).tags.intersect(it.tags).isNotEmpty()
        }
        (frg_product_list.adapter as ProductsAdapter).products = filter
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
            filterRecycler()
        }
    }

}