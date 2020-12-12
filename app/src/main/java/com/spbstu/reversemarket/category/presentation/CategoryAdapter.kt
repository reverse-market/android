package com.spbstu.reversemarket.category.presentation

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.di.NetworkModule

class CategoryAdapter(
    initTags: List<Category>,
    private val listener: (Int) -> Unit,
    private val glide: RequestManager
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
        with(holder) {
            name.text = categories[position].name
            name.setOnClickListener { listener(position) }
            glide.load(NetworkModule.DATA_BASE_URL + categories[position].photo)
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        name.background = resource
                    }

                })
        }
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.layout_categories_item__name)
    }

}