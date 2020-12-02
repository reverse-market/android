package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.domain.model.Category

class CategoryFragment : Fragment() {

    private lateinit var categoriesRecycle: RecyclerView
    private lateinit var titleTextView: TextView
    private lateinit var searchBtn: View
    private lateinit var settingsBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        categoriesRecycle = view.findViewById(R.id.frg_category__list)
        categoriesRecycle.adapter = CategoryAdapter(
            provideCategories(),
            requireContext(),
            ::provideCategoryClickListener
        )
        titleTextView = view.findViewById(R.id.layout_toolbar_search__category_name)
        titleTextView.setOnClickListener {
            val args = Bundle()
            args.putString(CATEGORY_NAV_PARAMETER, titleTextView.text.toString())
            findNavController().navigate(R.id.navigation_sell, args)
        }
        arguments?.getString(CATEGORY_NAV_PARAMETER)?.let {
            titleTextView.text = it
        }
        searchBtn = view.findViewById(R.id.layout_toolbar_search__full_btn)
        searchBtn.visibility = View.INVISIBLE
        settingsBtn = view.findViewById(R.id.layout_toolbar_search__btn)
        settingsBtn.visibility = View.INVISIBLE

        return view
    }

    private fun provideCategoryClickListener(position: Int) {
        val args = Bundle()
        val categoryName = (categoriesRecycle.adapter as CategoryAdapter).categories[position].name
        args.putString(CATEGORY_NAV_PARAMETER, categoryName)
        findNavController().navigate(R.id.navigation_sell, args)
    }

    private fun provideCategories(): List<Category> =
        listOf(
            Category("Всё",""),
            Category("Недвижимость",""),
            Category("Электроника",""),
            Category("Хобби и отдых",""),
            Category("Транспорт",""),
            Category("Одежда",""),
            Category("Животные",""),
            Category("Для дома",""),
            Category("Прочее","")
        )

    companion object {
        const val CATEGORY_NAV_PARAMETER = "CATEGORY"
    }
}