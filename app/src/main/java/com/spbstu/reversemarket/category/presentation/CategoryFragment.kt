package com.spbstu.reversemarket.category.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.layout_toolbar__search.*

class CategoryFragment : InjectionFragment<CategoryViewModel>(R.layout.fragment_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_toolbar_search__category_name.setOnClickListener {
            val args = Bundle()
            args.putString(CATEGORY_NAME, layout_toolbar_search__category_name.text.toString())
            findNavController().navigate(R.id.navigation_sell, args)
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
            frg_category__list.adapter =
                CategoryAdapter(
                    it,
                    ::provideCategoryClickListener,
                    Glide.with(this)
                )
        }
    }

    private fun provideCategoryClickListener(position: Int) {
        val args = Bundle()
        val categoryId = (frg_category__list.adapter as CategoryAdapter).categories[position].id
        val categoryName = (frg_category__list.adapter as CategoryAdapter).categories[position].name
        args.putInt(CATEGORY_ID, categoryId)
        args.putString(CATEGORY_NAME, categoryName)
        findNavController().navigate(R.id.navigation_sell, args)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY"
        const val CATEGORY_ID = "CATEGORY"
    }
}