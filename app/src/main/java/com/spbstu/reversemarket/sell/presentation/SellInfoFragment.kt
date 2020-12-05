package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.buy.presentation.AddressAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import com.spbstu.reversemarket.utils.Utils.Companion.changeImage

class SellInfoFragment : Fragment() {

    private lateinit var photosList: RecyclerView
    private lateinit var addressList: RecyclerView
    private lateinit var saveTemplateCheckBox: ImageView
    private lateinit var saveTemplateCheckBoxBackground: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sell_info, container, false)

        photosList = view.findViewById(R.id.layout_new_product__photo_list)
        photosList.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        )

        addressList = view.findViewById(R.id.layout_address_list)
        addressList.adapter = AddressAdapter(
            provideAddresses(),
            ::provideAddressClickListener
        )

        saveTemplateCheckBox = view.findViewById(R.id.frg_sell_info__checkbox)
        saveTemplateCheckBoxBackground = view.findViewById(R.id.frg_sell_info__checkbox_background)
        saveTemplateCheckBoxBackground.setOnClickListener { changeCheckBoxState() }

        return view
    }

    private fun provideUrlList(): List<String> = listOf("null")

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

    private fun changeCheckBoxState() {
        if (saveTemplateCheckBox.tag == SAVED_TEMPLATE_STATE_TAG) {
            changeImage(saveTemplateCheckBox, R.drawable.ic_unchecked, requireContext())
            saveTemplateCheckBox.tag = UNSAVED_TEMPLATE_STATE_TAG
        } else {
            changeImage(saveTemplateCheckBox, R.drawable.ic_checkbox, requireContext())
            saveTemplateCheckBox.tag = SAVED_TEMPLATE_STATE_TAG
        }
    }

    private fun provideAddressClickListener(position: Int) {

    }

    companion object {
        const val SAVED_TEMPLATE_STATE_TAG = "save"
        const val UNSAVED_TEMPLATE_STATE_TAG = "no save"
    }


}