package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView = view.findViewById(R.id.layout_toolbar_search__category_name)
        titleTextView.setText(R.string.frg_buy_title)
        titleTextView.setCompoundDrawables(null, null, null, null)

        productList = view.findViewById(R.id.frg_buy__list)
        productList.layoutManager = LinearLayoutManager(context)
        productList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            ) {
                val bundle = Bundle()
                bundle.putInt(ProductFragment.PRODUCT_ID, it.id)
                bundle.putString(ProductFragment.PRODUCT_NAME, it.name)
                bundle.putString(ProductFragment.PRODUCT_ITEM_NAME, it.itemName)
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
            findNavController().navigate(R.id.buyInfoFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.createRequest().observe(viewLifecycleOwner, Observer {
            (productList.adapter as ProductsAdapter).requests = it
        })
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
            provideProductTags2(),
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
        )
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
        val filter = provideProducts().filter {
            (it.name.contains(text, true) || it.itemName.contains(text, true))
        }
        (productList.adapter as ProductsAdapter).requests = filter
    }
}