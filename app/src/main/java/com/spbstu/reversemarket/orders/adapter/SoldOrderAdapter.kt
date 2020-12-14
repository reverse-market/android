package com.spbstu.reversemarket.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.card.MaterialCardView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.di.NetworkModule
import com.spbstu.reversemarket.orders.domain.SoldOrder
import com.spbstu.reversemarket.profile.data.model.Order

class SoldOrderAdapter(
    private val orders: List<Order>,
    private val glide: RequestManager,
    private val onClick: (Int, View) -> Unit
) :
    RecyclerView.Adapter<SoldOrderAdapter.SoldOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldOrderViewHolder =
        SoldOrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sold_order, parent, false
            )
        )

    override fun onBindViewHolder(holder: SoldOrderViewHolder, position: Int) {
        with(holder) {
            val order = orders[position]
            title.text = order.name
            subTitle.text = order.itemName
            amount.text = order.quantity.toString()
            price.text = order.price.toString()
            if (order.photos.isNotEmpty()) {
                val photo = order.photos[0]
                glide.load(NetworkModule.DATA_BASE_URL + photo).into(image)
            }
            imageCard.setOnClickListener { onClick.invoke(position, it) }
        }
    }

    override fun getItemCount(): Int = orders.size

    class SoldOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_sold__image)
        val title: TextView = view.findViewById(R.id.item_sold__title)
        val subTitle: TextView = view.findViewById(R.id.item_sold__sub_title)
        val amount: TextView = view.findViewById(R.id.item_sold__amount)
        val price: TextView = view.findViewById(R.id.item_sold__price)
        val imageCard: MaterialCardView = view.findViewById(R.id.item_sold__image_card)
    }
}