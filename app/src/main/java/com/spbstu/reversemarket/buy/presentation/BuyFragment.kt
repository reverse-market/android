package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.domain.model.Product
import com.spbstu.reversemarket.sell.presentation.ProductsAdapter
import com.spbstu.reversemarket.sell.presentation.RecyclerItemClickListener
import com.spbstu.reversemarket.utils.Utils
import com.spbstu.reversemarket.utils.Utils.Companion.closeSearchView
import com.spbstu.reversemarket.utils.Utils.Companion.showSearchView

class BuyFragment : Fragment() {

    private lateinit var buyViewModel: BuyViewModel
    private lateinit var titleTextView: TextView
    private lateinit var productList: RecyclerView
    private lateinit var searchButton: FrameLayout
    private lateinit var searchTextBackground: RelativeLayout
    private lateinit var searchText: EditText
    private lateinit var searchCloseBtn: ImageView
    private lateinit var addNewButton: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buyViewModel = ViewModelProvider(this).get(BuyViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_buy, container, false)
        titleTextView = view.findViewById(R.id.layout_toolbar_search__category_name)
        titleTextView.setText(R.string.frg_buy_title)
        titleTextView.setCompoundDrawables(null, null, null, null)

        productList = view.findViewById(R.id.frg_buy__list)
        productList.addOnItemTouchListener(
            RecyclerItemClickListener(productList,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        findNavController().navigate(R.id.action_navigation_sell_to_navigation_product)
                    }
                })
        )
        productList.layoutManager = LinearLayoutManager(context)
        productList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            )
        searchTextBackground = view.findViewById(R.id.layout_toolbar_search_text__background)
        searchText = view.findViewById(R.id.layout_toolbar_search__text)
        searchButton = view.findViewById(R.id.layout_toolbar_search__button)

        searchButton.setOnClickListener(searchButtonListener)
        searchText.setOnKeyListener(Utils(::filterRecycler).enterListener)

        searchCloseBtn = view.findViewById(R.id.layout_toolbar__search_close_btn)
        searchCloseBtn.setOnClickListener {
            closeSearchView(titleTextView, searchTextBackground, searchCloseBtn, searchText, activity)
        }
        addNewButton = view.findViewById(R.id.layout_toolbar_search__btn)
        addNewButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_add
            )
        )

        view.findViewById<ImageView>(R.id.layout_toolbar_search__btn).setOnClickListener {
            findNavController().navigate(R.id.buyInfoFragment)
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


    private val searchButtonListener = View.OnClickListener {
        if (titleTextView.visibility == View.VISIBLE) {
            showSearchView(titleTextView, searchTextBackground, searchText, searchCloseBtn, activity)
        } else {
            filterRecycler()
        }
    }

    private fun filterRecycler() {
        val text = searchText.text.toString().trim().toLowerCase()
        val filter = provideProducts().filter {
            (it.name.contains(text, true) || it.fullName.contains(text, true))
        }
        (productList.adapter as ProductsAdapter).products = filter
    }
}