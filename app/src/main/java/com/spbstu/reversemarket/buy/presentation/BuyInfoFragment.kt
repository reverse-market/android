package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.NO_MARGIN_FLAG
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.addTag
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.getFocusListener
import com.spbstu.reversemarket.utils.Utils

class BuyInfoFragment : Fragment() {

    private lateinit var photosList: RecyclerView
    private lateinit var addressList: RecyclerView
    private lateinit var search: EditText
    private lateinit var searchButtonBackground: RelativeLayout
    private lateinit var searchOpenLayout: View
    private lateinit var closeBtn: ImageView
    private lateinit var selectedTagsList: RecyclerView
    private lateinit var addTagsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buy_info, container, false)
        view.findViewById<ImageView>(R.id.frg_buy_info__back_btn).setOnClickListener {
            findNavController().navigate(R.id.navigation_buy)
        }

        photosList = view.findViewById(R.id.layout_new_product__photo_list)
        photosList.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        )

        addressList = view.findViewById(R.id.layout_address_list)
        addressList.adapter = AddressAdapter(
            provideAddresses(),
            ::provideAddressClickListener,
            R.id.buyInfoFragment
        )

        selectedTagsList = view.findViewById(R.id.frg_filter__selected_categories)
        selectedTagsList.adapter =
            TagsAdapter(
                provideSelectedList(),
                R.layout.layout_filter_selected_item,
                addFunc = { str: String -> addTag(addTagsList, str) },
            )

        searchOpenLayout = view.findViewById(R.id.frg_but_info__selected_tags)
        searchButtonBackground = view.findViewById(R.id.frg_but_info__search_text_background)
        search = view.findViewById(R.id.layout_search__toolbar_search__text)
        closeBtn = view.findViewById(R.id.layout_search__toolbar_search_close_btn)
        closeBtn.setOnClickListener { Utils.closeKeyboard(activity, search) }

        addTagsList = view.findViewById(R.id.layout_selected_tags__new_tags)
        addTagsList.adapter =
            TagsAdapter(
                provideAddTagsList(),
                R.layout.layout_add_tag,
                addFunc = { str: String -> addTag(selectedTagsList, str) },
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        addTagsList.layoutManager = layoutManager

        searchOpenLayout.viewTreeObserver.addOnGlobalLayoutListener(
            getFocusListener(
                search,
                searchButtonBackground,
                searchOpenLayout,
                closeBtn,
                NO_MARGIN_FLAG
            )
        )

        return view
    }

    private fun provideUrlList(): List<String> = listOf("null")

    private fun provideAddTagsList(): List<String> = listOf(
        "Кроссовки",
        "Костюмы",
        "Брюки",
        "Джинсы",
        "Шорты",
        "Носки",
        "Куртки",
        "Пальто",
        "Футболки",
        "Плавки"
    )

    private fun provideSelectedList(): List<String> =
        listOf("Обувь", "Кроссовки", "Санкт-Петербург")

    private fun provideAddresses(): List<Address> = listOf(
        Address(
            "Мой адрес",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.28, кв. 1, 190000",
            "Иванов Иван Иванович"
        ),
        Address(
            "Работа",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.35, кв. 30, 190000",
            "Иванов Иван Иванович"
        )
    )

    private fun provideAddressClickListener(position: Int) {

    }

}