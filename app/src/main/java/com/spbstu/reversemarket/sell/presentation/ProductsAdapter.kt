package com.spbstu.reversemarket.sell.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.domain.model.Product


class ProductsAdapter(
    initProducts: List<Product>,
    private val context: Context?
) : RecyclerView.Adapter<ProductsAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)
        return TagViewHolder(
            view
        )
    }

    var products: List<Product> = initProducts
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = products[position].name
        holder.fullName.text = products[position].fullName
        holder.viewAmount.text = products[position].viewAmount.toString()
        holder.productTags.adapter =
            TagsAdapter(
                products[position].tags,
                R.layout.layout_product_tag
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        holder.productTags.layoutManager = layoutManager
        holder.productDescription.text = products[position].description
        holder.price.text = products[position].price
        holder.userName.text = products[position].username
        holder.date.text = products[position].date
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.layout_product_item__name)
        val fullName: TextView = view.findViewById(R.id.layout_product_item__full_name)
        val viewAmount: TextView = view.findViewById(R.id.layout_product_item__view_amount)
        val productTags: RecyclerView = view.findViewById(R.id.layout_product_item__tags)
        val productDescription: TextView = view.findViewById(R.id.layout_product_item__description)
        val price: TextView = view.findViewById(R.id.layout_product_item__price)
        val userName: TextView = view.findViewById(R.id.layout_product_item__username)
        val date: TextView = view.findViewById(R.id.layout_product_item__date)
    }
}