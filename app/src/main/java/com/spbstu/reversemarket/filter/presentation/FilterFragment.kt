package com.spbstu.reversemarket.filter.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.slider.RangeSlider
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.category.presentation.CategoryFragment.Companion.CATEGORY_ID
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.AddSearchViewUtils
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.addTag
import com.spbstu.reversemarket.utils.Utils.Companion.closeKeyboard
import com.spbstu.reversemarket.utils.Utils.Companion.initPrevTags
import com.spbstu.reversemarket.utils.Utils.Companion.provideTagsBundle
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_selected_tags.*

class FilterFragment : InjectionFragment<FilterViewModel>(R.layout.fragment_filter) {

    private var prevTags: MutableList<Tag> = mutableListOf()
    private var categoryId: Int = 0
    private var priceFrom: Int = 0
    private var priceTo = 100000
    private var sort = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE
        frg_filter__price_range_slider.addOnSliderTouchListener(sliderListener)
        frg_filter__back_btn.setOnClickListener(toSellFragmentBackClickListener)
        prevTags = initPrevTags(arguments)
        layout_selected_tags__new_tags.adapter =
            TagsAdapter(
                emptyList(),
                R.layout.layout_add_tag,
                addFunc = { tag: Tag -> addTag(frg_filter__selected_categories, tag) },
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        layout_selected_tags__new_tags.layoutManager = layoutManager

        frg_filter__selected_categories.adapter =
            TagsAdapter(
                prevTags,
                R.layout.layout_filter_selected_item,
                addFunc = { tag: Tag -> addTag(layout_selected_tags__new_tags, tag) },
            )

        frg_filter__min_price.addTextChangedListener(provideEditTextListener(SLIDER_MIN_VALUE_INDEX))
        frg_filter__max_price.addTextChangedListener(provideEditTextListener(SLIDER_MAX_VALUE_INDEX))

        frg_filter__sorting_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        frg_filter__sorting_list.adapter =
            TagsAdapter(
                provideSorting(),
                R.layout.layout_sorting_list_item,
                context = activity?.applicationContext,
            )

        layout_search__toolbar_search_close_btn.setOnClickListener {
            closeKeyboard(
                activity,
                layout_search__toolbar_search__text
            )
        }
        frg_filter_selected_tags.viewTreeObserver.addOnGlobalLayoutListener(
            AddSearchViewUtils.getFocusListener(
                layout_search__toolbar_search__text,
                frg_filter__search_text_background,
                frg_filter_selected_tags,
                layout_search__toolbar_search_close_btn,
                resources.getDimension(R.dimen.def_dimen).toInt()
            )
        )
        frg_filter__save_btn.setOnClickListener(toSellFragmentSaveClickListener)
        initParams()
    }

    private fun initParams() {
        priceFrom = requireArguments().getInt(PRICE_FROM)
        priceTo = requireArguments().getInt(PRICE_TO)
        sort = requireArguments().getString(SORT)!!
        categoryId = requireArguments().getInt(CATEGORY_ID)
        frg_filter__min_price.setText(priceFrom.toString())
        frg_filter__max_price.setText(priceTo.toString())
        parseSort(sort)
    }

    private fun parseSort(sort: String) {
        val spaceIndex = sort.indexOf("_")
        val sortType = sort.substring(0, spaceIndex)
        (frg_filter__sorting_list.adapter as TagsAdapter).isAsc =
            sort.substring(spaceIndex + 1) == "asc"
        (frg_filter__sorting_list.adapter as TagsAdapter).selectedItem = mapSortToIndex(sortType)
    }

    private fun mapSortToIndex(str: String): Int {
        return when(str) {
            SORTING_PRICE_SELECTED_STRING -> SORTING_PRICE_SELECTED
            SORTING_VIEWS_SELECTED_STRING -> SORTING_VIEWS_SELECTED
            SORTING_DATE_SELECTED_STRING -> SORTING_DATE_SELECTED
            else -> -1
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getTags(requireArguments().getInt(CATEGORY_ID)).observe(viewLifecycleOwner) {
            (layout_selected_tags__new_tags.adapter as TagsAdapter).tags = it
        }
    }

    override fun onStop() {
        closeKeyboard(activity, layout_search__toolbar_search__text)
        super.onStop()
    }

    private val toSellFragmentSaveClickListener = View.OnClickListener {
        val filterTags = (frg_filter__selected_categories.adapter as TagsAdapter).tags
        val bundle = provideTagsBundle(filterTags)
        addSortingParamsToBundle(bundle)
        findNavController(requireView()).navigate(R.id.navigation_sell, bundle)
    }

    private val toSellFragmentBackClickListener = View.OnClickListener {
        val bundle = provideTagsBundle(prevTags)
        bundle.putInt(PRICE_FROM, priceFrom)
        bundle.putInt(PRICE_TO, priceTo)
        bundle.putString(SORT, sort)
        findNavController(requireView()).navigate(R.id.navigation_sell, bundle)
    }

    private fun addSortingParamsToBundle(bundle: Bundle) {
        val from = frg_filter__min_price.text.toString()
        val to = frg_filter__max_price.text.toString()
        bundle.putInt(PRICE_FROM, if (from.isEmpty()) SLIDER_LEFT_BOUND else from.toInt())
        bundle.putInt(PRICE_TO, if (from.isEmpty()) SLIDER_MAX_VALUE_INDEX else to.toInt())
        val sort = getSortingParam()
        bundle.putString(SORT, sort)
    }



    private fun getSortingParam(): String {
        val adapter = (frg_filter__sorting_list.adapter as TagsAdapter)
        val sort = when (adapter.selectedItem) {
            SORTING_PRICE_SELECTED -> "price_"
            SORTING_VIEWS_SELECTED -> "views_"
            SORTING_DATE_SELECTED -> "date_"
            else -> "wrong"
        }
        return sort + if (adapter.isAsc) "asc" else "desc"
    }

    private fun provideSorting(): List<Tag> =
        listOf(Tag(0, "Цена"), Tag(0, "Просмотры"), Tag(0, "Дата"))

    private fun provideSelectedList(): List<Tag> =
        listOf(Tag(0, "Обувь"), Tag(0, "Кроссовки"), Tag(0, "Санкт-Петербург"))

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

    private val sliderListener = object : RangeSlider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: RangeSlider) =
            changePriceBounds(slider)

        override fun onStopTrackingTouch(slider: RangeSlider) =
            changePriceBounds(slider)
    }

    private fun changePriceBounds(slider: RangeSlider) {
        val values = slider.values
        frg_filter__min_price.setText(values[0].toLong().toString())
        frg_filter__max_price.setText(values[1].toLong().toString())
    }

    private fun provideEditTextListener(sliderIndex: Int) =
        object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val num: String = s.toString()
                val trimmedNum = num.trim()
                if (trimmedNum.isNotEmpty()) {
                    val value = trimmedNum.toFloat()
                    if ((value >= SLIDER_LEFT_BOUND && value <= SLIDER_RIGHT_BOUND)
                        && isRightSliderInvariant(sliderIndex, value)
                    ) {
                        if (sliderIndex == 0) {
                            frg_filter__price_range_slider.values = listOf(
                                value,
                                frg_filter__price_range_slider.values[1]
                            )
                        } else {
                            frg_filter__price_range_slider.values = listOf(
                                frg_filter__price_range_slider.values[0],
                                value
                            )
                        }
                    }
                }
            }
        }

    private fun isRightSliderInvariant(
        sliderIndex: Int,
        value: Float
    ) =
        (sliderIndex == SLIDER_MIN_VALUE_INDEX && value <= frg_filter__price_range_slider.values[1]
                || sliderIndex == SLIDER_MAX_VALUE_INDEX && value >= frg_filter__price_range_slider.values[0])

    companion object {
        const val SLIDER_MIN_VALUE_INDEX = 0
        const val SLIDER_MAX_VALUE_INDEX = 1
        const val SLIDER_LEFT_BOUND = 0
        const val SLIDER_RIGHT_BOUND = 100000
        const val FILTER_TAGS_IDS = "FILTER_TAGS_IDS"
        const val FILTER_TAGS_NAME = "FILTER_TAGS_NAME"
        const val PRICE_FROM = "PRICE_FROM"
        const val PRICE_TO = "PRICE_TO"
        const val SORT = "SORT"
        const val SORTING_PRICE_SELECTED = 0
        const val SORTING_VIEWS_SELECTED = 1
        const val SORTING_DATE_SELECTED = 2
        const val SORTING_PRICE_SELECTED_STRING = "price"
        const val SORTING_VIEWS_SELECTED_STRING = "views"
        const val SORTING_DATE_SELECTED_STRING = "date"
    }
}