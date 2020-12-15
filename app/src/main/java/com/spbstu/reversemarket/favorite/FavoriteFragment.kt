package com.spbstu.reversemarket.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.data.model.Request
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
                context,
                Glide.with(this)
            )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frg_favorite__back_btn.setOnClickListener {
            findNavController().popBackStack()
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
            3
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