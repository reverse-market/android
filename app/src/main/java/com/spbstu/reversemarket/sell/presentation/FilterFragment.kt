package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.RangeSlider
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.profile.ProfileViewModel

class FilterFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var slider: RangeSlider
    private lateinit var backBtn: ImageView
    private lateinit var selectedTagsList: RecyclerView
    private lateinit var sortingList: RecyclerView
    private lateinit var minPriceEditText: EditText
    private lateinit var maxPriceEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        slider = view.findViewById(R.id.frg_filter__price_range_slider)
        slider.addOnSliderTouchListener(sliderListener)

        backBtn = view.findViewById(R.id.frg_filter__back_btn)
        backBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigation_sell, null))

        selectedTagsList = view.findViewById(R.id.frg_filter__selected_categories)
        selectedTagsList.layoutManager = LinearLayoutManager(context)
        selectedTagsList.adapter =
            TagsAdapter(
                provideSelectedList(),
                R.layout.layout_filter_selected_item,
            )

        minPriceEditText = view.findViewById(R.id.frg_filter__min_price)
        maxPriceEditText = view.findViewById(R.id.frg_filter__max_price)

        sortingList = view.findViewById(R.id.frg_filter__sorting_list)
        sortingList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sortingList.adapter =
            TagsAdapter(
                provideSorting(),
                R.layout.layout_sorting_list_item,
                null,
                AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rotate_item),
                context
            )
        return view
    }


    private fun provideSorting(): List<String> = listOf("Цена", "Просмотры", "Дата")

    private fun provideSelectedList(): List<String> = listOf("Обувь", "Кроссовки", "Санкт-Петербург")


    private val sliderListener = object : RangeSlider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: RangeSlider) = changePriceBounds(slider)
        override fun onStopTrackingTouch(slider: RangeSlider) = changePriceBounds(slider)
    }

    private fun changePriceBounds(slider: RangeSlider) {
        val values = slider.values
        minPriceEditText.setText(values[0].toLong().toString())
        maxPriceEditText.setText(values[1].toLong().toString())
    }
}