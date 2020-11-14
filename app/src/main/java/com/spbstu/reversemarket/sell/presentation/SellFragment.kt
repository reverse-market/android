package com.spbstu.reversemarket.sell.presentation

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.domain.model.Product


class SellFragment : Fragment() {

    private lateinit var sellViewModel: SellViewModel
    private lateinit var productList: RecyclerView
    private lateinit var tagsList: RecyclerView
    private lateinit var searchButton: FrameLayout
    private lateinit var categoryNameToolbar: TextView
    private lateinit var searchTextBackground: FrameLayout
    private lateinit var searchText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_sell, container, false)
        val toolbar: Toolbar = view.findViewById(R.id.frg_search_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        productList = view.findViewById(R.id.frg_product_list)
        tagsList = view.findViewById(R.id.frg_tags_list)
        productList.layoutManager = LinearLayoutManager(context)
        productList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            )
        tagsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tagsList.adapter =
            TagsAdapter(
                provideTags(),
                R.layout.layout_removable_product_tag,
                ::filterRecycler
            )

        categoryNameToolbar = view.findViewById(R.id.layout_toolbar_search__category_name)
        searchTextBackground = view.findViewById(R.id.layout_toolbar_search_text__background)
        searchText = view.findViewById(R.id.layout_toolbar_search__text)
        searchButton = view.findViewById(R.id.layout_toolbar_search__button)

        searchButton.setOnClickListener(searchButtonListener)
        searchText.setOnKeyListener(enterListener)
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
            var inTags = false
            for (tag in (tagsList.adapter as TagsAdapter).tags) {
                if (it.tags.contains(tag)) {
                    inTags = true
                    break
                }
            }
            (it.name.toLowerCase().contains(text) ||
                    it.fullName.toLowerCase().contains(text)) && inTags
        }
        (productList.adapter as ProductsAdapter).products = filter
    }

    private val searchButtonListener = View.OnClickListener  {
        if (categoryNameToolbar.visibility == View.VISIBLE) {
            categoryNameToolbar.visibility = View.GONE
            searchTextBackground.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.scale_search)
            searchTextBackground.startAnimation(anim)
            val imm =
                activity?.applicationContext!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            searchText.requestFocus()
        } else {
            filterRecycler()
        }
    }

    private val enterListener = View.OnKeyListener { v, keyCode, event ->
        if ((event.action == KeyEvent.ACTION_DOWN) &&
            (keyCode == KeyEvent.KEYCODE_ENTER)) {
            filterRecycler()
            return@OnKeyListener true
        }
        return@OnKeyListener false
    }

}