package com.spbstu.reversemarket.category.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.filter.presentation.FilterFragment
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.layout_toolbar__search.*

class CategoryFragment : InjectionFragment<CategoryViewModel>(R.layout.fragment_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_toolbar_search__category_name.setOnClickListener {
            findNavController().popBackStack()
        }
        arguments?.getString(CATEGORY_NAME)?.let {
            layout_toolbar_search__category_name.text = it
        }
        layout_toolbar_search__full_btn.visibility = View.INVISIBLE
        layout_toolbar_search__btn.visibility = View.INVISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getCategories().observe(viewLifecycleOwner) {
            if (it != null) {
                frg_category__progress.isVisible = false
                frg_category__list.adapter =
                    CategoryAdapter(
                        it,
                        ::provideCategoryClickListener,
                        Glide.with(this)
                    )
            } else {
                frg_category__progress.isVisible = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun provideCategoryClickListener(position: Int) {
        val args = Bundle()
        val categoryId = (frg_category__list.adapter as CategoryAdapter).categories[position].id
        val categoryName = (frg_category__list.adapter as CategoryAdapter).categories[position].name
        args.putInt(CATEGORY_ID, categoryId)
        args.putString(CATEGORY_NAME, categoryName)
        args.putInt(FilterFragment.PRICE_TO, requireArguments().getInt(FilterFragment.PRICE_TO))
        args.putInt(FilterFragment.PRICE_FROM, requireArguments().getInt(FilterFragment.PRICE_FROM))
        if (categoryId == requireArguments().getInt(CATEGORY_ID)) {
            findNavController().popBackStack()
        } else {
            findNavController().navigate(R.id.action_categoryFragment_to_navigation_sell, args)
        }
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
        const val CATEGORY_ID = "CATEGORY_ID"
    }
}