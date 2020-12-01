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
import com.spbstu.reversemarket.orders.domain.BoughtOrder

class BoughtOrderAdapter(
    private val orders: List<BoughtOrder>,
    private val glide: RequestManager,
    private val onClick: (Int, View) -> Unit
) :
    RecyclerView.Adapter<BoughtOrderAdapter.BoughtOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoughtOrderViewHolder =
        BoughtOrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_bought_order, parent, false
            )
        )

    override fun onBindViewHolder(holder: BoughtOrderViewHolder, position: Int) {
        with(holder) {
            val order = orders[position]
            title.text = order.title
            subTitle.text = order.description
            glide.load(order.image).into(image)
            amount.text = order.amount.toString()
            price.text = order.price.toString()
            imageCard.setOnClickListener { onClick.invoke(position, it) }
        }
    }

    override fun getItemCount(): Int = orders.size

    class BoughtOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_bought__image)
        val title: TextView = view.findViewById(R.id.item_bought__title)
        val subTitle: TextView = view.findViewById(R.id.item_bought__sub_title)
        val amount: TextView = view.findViewById(R.id.item_bought__amount)
        val price: TextView = view.findViewById(R.id.item_bought__price)
        val imageCard: MaterialCardView = view.findViewById(R.id.item_bought__image_card)
    }
}