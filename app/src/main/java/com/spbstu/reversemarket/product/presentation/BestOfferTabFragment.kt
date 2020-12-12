package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.data.model.Request
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter

class BestOfferTabFragment : Fragment() {
    private lateinit var productList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_best_offer, container, false)
        productList = view.findViewById(R.id.layout_best_offer__buy__list)

        productList.layoutManager = LinearLayoutManager(context)
        productList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            )
        return view
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
}