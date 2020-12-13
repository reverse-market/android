package com.spbstu.reversemarket.buy.presentation

import android.content.Intent
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
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.NO_MARGIN_FLAG
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.addTag
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.getFocusListener
import com.spbstu.reversemarket.utils.Utils
import kotlinx.android.synthetic.main.fragment_buy_info.*
import kotlinx.android.synthetic.main.layout_new_product.view.*
import kotlinx.android.synthetic.main.layout_photos.*
import kotlinx.android.synthetic.main.layout_photos.view.*


class BuyInfoFragment : Fragment() {

    companion object {
        const val PICK_IMAGE = 50123
    }

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
            emptyList(),
            ::provideAddressClickListener,
            R.id.buyInfoFragment
        )

        selectedTagsList = view.findViewById(R.id.frg_filter__selected_categories)
        selectedTagsList.adapter =
            TagsAdapter(
                provideSelectedList(),
                R.layout.layout_filter_selected_item,
                addFunc = { tag: Tag -> addTag(addTagsList, tag) },
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
                addFunc = { tag: Tag -> addTag(selectedTagsList, tag) },
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_new_product__photo_list.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        ) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val uri = data?.data
            if (uri != null) {
                val adapter = layout_new_product__photo_list.adapter as PhotoAdapter
                val newList = mutableListOf<String>()
                newList.addAll(adapter.urls)
                newList.add(uri.toString())
                adapter.urls = newList
            }
        }
    }

    private fun provideUrlList(): List<String> = listOf("null")

    private fun provideAddTagsList(): List<Tag> = listOf(
        Tag(0, "Кроссовки"),
        Tag(0, "Костюмы"),
        Tag(0, "Брюки"),
        Tag(0, "Джинсы"),
        Tag(0, "Шорты"),
        Tag(0, "Носки"),
        Tag(0, "Куртки"),
        Tag(0, "Пальто"),
        Tag(0, "Футболки"),
        Tag(0, "Плавки")
    )

    private fun provideSelectedList(): List<Tag> =
        listOf(Tag(0, "Обувь"), Tag(0, "Кроссовки"), Tag(0, "Санкт-Петербург"))

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