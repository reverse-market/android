package com.spbstu.reversemarket.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.domain.model.Product
import com.spbstu.reversemarket.sell.presentation.RecyclerItemClickListener
import com.spbstu.reversemarket.sell.presentation.adapter.ProductsAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    private lateinit var favoriteList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteList = view.findViewById(R.id.frg_favorite__list)
        favoriteList.layoutManager = LinearLayoutManager(context)
        favoriteList.adapter =
            ProductsAdapter(
                provideProducts(),
                context
            )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frg_favorite__back_btn.setOnClickListener {
            findNavController().popBackStack()
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

}