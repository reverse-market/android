package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_DATE
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_DESCRIPTION
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_PRICE
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_QUANTITY
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_TAGS_NAME
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.layout_product_info.*

class DescriptionTabFragment(private val bundle: Bundle) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_product_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_product_info__description.text = bundle.getString(PRODUCT_DESCRIPTION)
        layout_product_info__price_number.text = bundle.getInt(PRODUCT_PRICE).toString()
        layout_product_info__amount_number.text = bundle.getInt(PRODUCT_QUANTITY).toString()
        layout_product_info__date_numbers.text = bundle.getString(PRODUCT_DATE)
        layout_product_info__tags_list.adapter =
            TagsAdapter(
                bundle.getStringArray(PRODUCT_TAGS_NAME)?.toList()?.map { Tag(0, it) } ?: emptyList(),
                R.layout.layout_product_tag,
                context = context
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        layout_product_info__tags_list.layoutManager = layoutManager
    }
}