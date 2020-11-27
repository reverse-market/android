package com.spbstu.reversemarket.sell.presentation

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.slider.RangeSlider
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.profile.ProfileViewModel


class FilterFragment : Fragment() {

    private lateinit var slider: RangeSlider
    private lateinit var backBtn: ImageView
    private lateinit var saveBtn: Button
    private lateinit var selectedTagsList: RecyclerView
    private lateinit var addTagsList: RecyclerView
    private lateinit var sortingList: RecyclerView
    private lateinit var minPriceEditText: EditText
    private lateinit var maxPriceEditText: EditText
    private lateinit var search: EditText
    private lateinit var searchButtonBackground: FrameLayout
    private lateinit var searchOpenLayout: View
    private var prevTags: List<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE
        slider = view.findViewById(R.id.frg_filter__price_range_slider)
        slider.addOnSliderTouchListener(sliderListener)

        backBtn = view.findViewById(R.id.frg_filter__back_btn)
        backBtn.setOnClickListener(toSellFragmentBackClickListener)

        prevTags = arguments?.getStringArray("FILTER_TAGS")?.toList()
        selectedTagsList = view.findViewById(R.id.frg_filter__selected_categories)
        selectedTagsList.layoutManager = LinearLayoutManager(context)
        selectedTagsList.adapter =
            TagsAdapter(
                prevTags?: provideSelectedList(),
                R.layout.layout_filter_selected_item,
                addFunc = { str: String ->  addTag(addTagsList, str)},
            )

        minPriceEditText = view.findViewById(R.id.frg_filter__min_price)
        minPriceEditText.addTextChangedListener(provideEditTextListener(SLIDER_MIN_VALUE_INDEX))
        maxPriceEditText = view.findViewById(R.id.frg_filter__max_price)
        maxPriceEditText.addTextChangedListener(provideEditTextListener(SLIDER_MAX_VALUE_INDEX))

        sortingList = view.findViewById(R.id.frg_filter__sorting_list)
        sortingList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sortingList.adapter =
            TagsAdapter(
                provideSorting(),
                R.layout.layout_sorting_list_item,
                context = activity?.applicationContext,
            )

        searchOpenLayout = view.findViewById(R.id.frg_filter_selected_tags)
        searchButtonBackground = view.findViewById(R.id.frg_filter__search_text_background)
        search = view.findViewById(R.id.frg_filter__toolbar_search__text)
        addTagsList = view.findViewById(R.id.layout_selected_tags__new_tags)
        addTagsList.adapter =
            TagsAdapter(
                provideAddTagsList(),
                R.layout.layout_add_tag,
                addFunc = { str: String ->  addTag(selectedTagsList, str)},
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        addTagsList.layoutManager = layoutManager

        searchOpenLayout.viewTreeObserver.addOnGlobalLayoutListener {
            if (search.isFocused) {
                if (keyboardShown(searchOpenLayout.rootView)) {
                    searchButtonBackground.setBackgroundResource(R.drawable.search_background_filter)
                    (searchButtonBackground.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                        0
                    searchOpenLayout.visibility = View.VISIBLE
                    Log.d("keyboard", "keyboard UP")
                } else {
                    searchButtonBackground.setBackgroundResource(R.drawable.search_background)
                    (searchButtonBackground.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            8f,
                            resources.displayMetrics
                        )
                            .toInt()
                    searchOpenLayout.visibility = View.GONE
                    Log.d("keyboard", "keyboard Down")
                }
            }
        }

        saveBtn = view.findViewById(R.id.frg_filter__save_btn)
        saveBtn.setOnClickListener(toSellFragmentSaveClickListener)
        return view
    }


    private val toSellFragmentSaveClickListener = View.OnClickListener {
        val args = Bundle()
        val filterTags = (selectedTagsList.adapter as TagsAdapter).tags
        args.putStringArray("FILTER_TAGS", filterTags.toTypedArray())
        Navigation.findNavController(requireView()).navigate(R.id.navigation_sell, args)
    }

    private val toSellFragmentBackClickListener = View.OnClickListener {
        val args = Bundle()
        args.putStringArray("FILTER_TAGS", prevTags?.toTypedArray())
        Navigation.findNavController(requireView()).navigate(R.id.navigation_sell, args)
    }

    private fun addTag(recycle: RecyclerView, tag: String) {
        val adapter = (recycle.adapter as TagsAdapter)
        val list = adapter.tags.toMutableList()
        list.add(tag)
        adapter.tags = list
        adapter.notifyDataSetChanged()
    }

    private fun keyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff: Int = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }

    private fun provideSorting(): List<String> = listOf("Цена", "Просмотры", "Дата")

    private fun provideSelectedList(): List<String> =
        listOf("Обувь", "Кроссовки", "Санкт-Петербург")

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

    private val sliderListener = object : RangeSlider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: RangeSlider) = changePriceBounds(slider)
        override fun onStopTrackingTouch(slider: RangeSlider) = changePriceBounds(slider)
    }

    private fun changePriceBounds(slider: RangeSlider) {
        val values = slider.values
        minPriceEditText.setText(values[0].toLong().toString())
        maxPriceEditText.setText(values[1].toLong().toString())
    }

    private fun provideEditTextListener(sliderIndex: Int) = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val num: String = s.toString()
            println(num)
            val value = num.trim().toFloat()
            println(value)
            if (value in SLIDER_LEFT_BOUND..SLIDER_RIGHT_BOUND
                    && isRightSliderInvariant(sliderIndex, value)) {
                if (sliderIndex == 0) {
                    slider.values = listOf(value, slider.values[1])
                } else {
                    slider.values = listOf(slider.values[0], value)
                }
            }
        }
    }

    private fun isRightSliderInvariant(sliderIndex: Int, value: Float)
        = (sliderIndex == SLIDER_MIN_VALUE_INDEX && value <= slider.values[1]
            || sliderIndex == SLIDER_MAX_VALUE_INDEX && value >= slider.values[0])

    companion object {
        const val SLIDER_MIN_VALUE_INDEX = 0
        const val SLIDER_MAX_VALUE_INDEX = 1
        const val SLIDER_LEFT_BOUND = 0.0
        const val SLIDER_RIGHT_BOUND = 100000.0
    }
}