package com.spbstu.reversemarket.sell.presentation.adapter

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
import com.spbstu.reversemarket.sell.data.model.Request


class ProductsAdapter(
    initRequests: List<Request>,
    private val context: Context?,
    private val onClick: ((Request) -> Unit)? = null
) : RecyclerView.Adapter<ProductsAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)
        return TagViewHolder(
            view
        )
    }

    var requests: List<Request> = initRequests
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = requests.size


    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = requests[position].name
        holder.fullName.text = requests[position].itemName
        holder.productTags.adapter =
            TagsAdapter(
                requests[position].tags,
                R.layout.layout_product_tag,
                context = context
            )
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        holder.productTags.layoutManager = layoutManager
        holder.productDescription.text = requests[position].description
        holder.price.text = requests[position].price.toString()
        holder.userName.text = requests[position].userName
        holder.date.text = requests[position].date
        holder.itemView.setOnClickListener {
            onClick?.invoke(requests[position])
        }
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.layout_product_item__name)
        val fullName: TextView = view.findViewById(R.id.layout_product_item__full_name)
        val productTags: RecyclerView = view.findViewById(R.id.layout_product_item__tags)
        val productDescription: TextView = view.findViewById(R.id.layout_product_item__description)
        val price: TextView = view.findViewById(R.id.layout_product_item__price)
        val userName: TextView = view.findViewById(R.id.layout_product_item__username)
        val date: TextView = view.findViewById(R.id.layout_product_item__date)
    }
}