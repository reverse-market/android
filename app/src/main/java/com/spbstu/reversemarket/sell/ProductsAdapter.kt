package com.spbstu.reversemarket.sell

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


class ProductsAdapter(
    initProducts: List<Product>,
    private val context: Context?
) : RecyclerView.Adapter<ProductsAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sell_product_item_layout, parent, false);
        return TagViewHolder(view)
    }

    var products: List<Product> = initProducts
        set(value) {
            field = value
            notifyDataSetChanged();
        }


    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = products[position].name
        holder.fullName.text = products[position].fullName
        holder.viewAmount.text = products[position].viewAmount.toString()
        holder.productTags.adapter = TagsAdapter(
            products[position].tags,
            R.layout.product_tag_layout
        )
//        holder.productTags.layoutManager = LinearLayoutManager(context, GridLayoutManager.HORIZONTAL, false)
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
        val name: TextView = view.findViewById(R.id.name)
        val fullName: TextView = view.findViewById(R.id.full_name)
        val viewAmount: TextView = view.findViewById(R.id.view_amount)
        val productTags: RecyclerView = view.findViewById(R.id.product_tag_bar)
        val productDescription: TextView = view.findViewById(R.id.product_description)
        val price: TextView = view.findViewById(R.id.price)
        val userName: TextView = view.findViewById(R.id.username)
        val date: TextView = view.findViewById(R.id.date)
    }
}