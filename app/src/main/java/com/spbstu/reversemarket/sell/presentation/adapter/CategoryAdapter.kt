package com.spbstu.reversemarket.sell.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.domain.model.Category

class CategoryAdapter(
    initTags: List<Category>,
    private val context: Context,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categories: List<Category> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_categories_item, parent, false)
        return CategoryViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.name.text = categories[position].name
        holder.name.background = ContextCompat.getDrawable(context, R.drawable.category_background)
        holder.name.setOnClickListener { listener(position) }
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.layout_categories_item__name)
    }

}